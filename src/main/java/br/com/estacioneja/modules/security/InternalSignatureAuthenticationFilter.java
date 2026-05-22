package br.com.estacioneja.modules.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Component
public class InternalSignatureAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(InternalSignatureAuthenticationFilter.class);

    private static final String HEADER_CALLER = "X-Internal-Caller";
    private static final String HEADER_TIMESTAMP = "X-Internal-Timestamp";
    private static final String HEADER_NONCE = "X-Internal-Nonce";
    private static final String HEADER_SIGNATURE = "X-Internal-Signature";

    private static final long MAX_CLOCK_SKEW_SECONDS = 300L;
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String INTERNAL_ROLE = "ROLE_INTERNAL_SERVICE";

    private final String signatureSecret;

    public InternalSignatureAuthenticationFilter(@Value("${estacioneja.internal.signature-secret}") String signatureSecret) {
        this.signatureSecret = signatureSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String caller = request.getHeader(HEADER_CALLER);
        if (caller == null || caller.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        String timestamp = request.getHeader(HEADER_TIMESTAMP);
        String nonce = request.getHeader(HEADER_NONCE);
        String signature = request.getHeader(HEADER_SIGNATURE);

        if (timestamp == null || timestamp.isBlank()
                || nonce == null || nonce.isBlank()
                || signature == null || signature.isBlank()) {
            log.warn("Internal signature: cabecalhos obrigatorios ausentes (caller={})", caller);
            unauthorized(response);
            return;
        }

        long ts;
        try {
            ts = Long.parseLong(timestamp.trim());
        } catch (NumberFormatException e) {
            log.warn("Internal signature: timestamp invalido (caller={}, value={})", caller, timestamp);
            unauthorized(response);
            return;
        }

        long now = System.currentTimeMillis() / 1000L;
        if (Math.abs(now - ts) > MAX_CLOCK_SKEW_SECONDS) {
            log.warn("Internal signature: timestamp fora da janela (caller={}, skew={}s)", caller, now - ts);
            unauthorized(response);
            return;
        }

        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);
        byte[] body = cachedRequest.getCachedBody();

        String bodyHash;
        try {
            bodyHash = sha256Hex(body);
        } catch (NoSuchAlgorithmException e) {
            log.error("Internal signature: SHA-256 indisponivel", e);
            unauthorized(response);
            return;
        }

        String stringToSign = request.getMethod()
                + "\n" + request.getRequestURI()
                + "\n" + timestamp
                + "\n" + nonce
                + "\n" + bodyHash;

        String expected;
        try {
            expected = hmacSha256Hex(signatureSecret, stringToSign);
        } catch (Exception e) {
            log.error("Internal signature: falha ao calcular HMAC", e);
            unauthorized(response);
            return;
        }

        byte[] expectedBytes = expected.getBytes(StandardCharsets.US_ASCII);
        byte[] receivedBytes = signature.toLowerCase().getBytes(StandardCharsets.US_ASCII);

        if (!MessageDigest.isEqual(expectedBytes, receivedBytes)) {
            log.warn("Internal signature: assinatura invalida (caller={}, method={}, path={})",
                    caller, request.getMethod(), request.getRequestURI());
            unauthorized(response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                "internal:" + caller,
                null,
                List.of(new SimpleGrantedAuthority(INTERNAL_ROLE))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(cachedRequest, response);
    }

    private void unauthorized(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"unauthorized\"}");
    }

    private static String sha256Hex(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return toHexLower(md.digest(data));
    }

    private static String hmacSha256Hex(String secret, String message) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM));
        return toHexLower(mac.doFinal(message.getBytes(StandardCharsets.UTF_8)));
    }

    private static String toHexLower(byte[] bytes) {
        char[] hex = new char[bytes.length * 2];
        char[] digits = "0123456789abcdef".toCharArray();
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hex[i * 2] = digits[v >>> 4];
            hex[i * 2 + 1] = digits[v & 0x0F];
        }
        return new String(hex);
    }

    private static final class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
        private final byte[] cachedBody;

        CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
            super(request);
            this.cachedBody = request.getInputStream().readAllBytes();
        }

        byte[] getCachedBody() {
            return cachedBody;
        }

        @Override
        public ServletInputStream getInputStream() {
            ByteArrayInputStream buffer = new ByteArrayInputStream(cachedBody);
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return buffer.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int read() {
                    return buffer.read();
                }
            };
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
        }
    }
}
