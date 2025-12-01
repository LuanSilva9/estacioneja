package br.com.estacioneja.infra.config.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import br.com.estacioneja.domain.enums.TipoUsuario;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public GoogleOAuth2SuccessHandler(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        Usuario user = usuarioRepository.findByEmail(email)
                .orElseGet(() -> usuarioRepository.save(
                        new Usuario(null, name, email, null, null, TipoUsuario.COMUM, null, null, null)
                ));

        String token = jwtService.generateToken(user);

        String redirectUri = request.getParameter("redirect_uri");

        if (redirectUri == null || redirectUri.isBlank()) {
            redirectUri = "http://localhost:3000/auth/callback";
        }

        // monta destino final
        String targetUrl = redirectUri + "?token=" + token;

        response.sendRedirect(targetUrl);
    }
}
