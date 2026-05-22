package br.com.estacioneja.modules.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.estacioneja.modules.security.dto.AuthDTO;
import br.com.estacioneja.modules.security.dto.JWTOutputDTO;
import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.modules.usuario.dto.CreateUsuarioDto;
import br.com.estacioneja.modules.usuario.dto.ReadUsuarioDto;
import br.com.estacioneja.modules.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Autenticação", description = "Endpoints públicos para login e registro de usuários")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;
    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    @SecurityRequirements
    @PostMapping("/login")
    public ResponseEntity<JWTOutputDTO> login(@Valid @RequestBody AuthDTO dto) {
        Usuario usuario = authService.loginAndReturnUser(dto.email(), dto.senha());

        String token = jwtService.generateToken(usuario);

        return ResponseEntity.ok(new JWTOutputDTO(token, usuario.getTipoUsuario().name()));
    }

    @SecurityRequirements
    @PostMapping("/register")
    public ResponseEntity<ReadUsuarioDto> register(@Valid @RequestBody CreateUsuarioDto dto) {
        ReadUsuarioDto userCreated = usuarioService.create(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.id())
                .toUri();

        return ResponseEntity.created(uri).body(userCreated);
    }

}
