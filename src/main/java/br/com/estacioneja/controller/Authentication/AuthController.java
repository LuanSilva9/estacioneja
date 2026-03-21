package br.com.estacioneja.controller.Authentication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.AuthDTO;
import br.com.estacioneja.dto.input.UsuarioDTO;
import br.com.estacioneja.dto.output.JWTOutputDTO;
import br.com.estacioneja.infra.config.security.AuthenticationService;
import br.com.estacioneja.infra.config.security.JwtService;
import br.com.estacioneja.services.Usuario.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    private final AuthenticationService authService;
    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public AuthController(AuthenticationService authService, UsuarioService usuarioService, JwtService jwtService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTOutputDTO> login(@RequestBody AuthDTO dto) {
        Usuario usuario = authService.loginAndReturnUser(dto.email(), dto.senha());

        String token = jwtService.generateToken(usuario);

        return ResponseEntity.ok(new JWTOutputDTO(token, usuario.getTipoUsuario().name()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioDTO dto) {
        usuarioService.create(dto);
        
        return ResponseEntity.ok().build();
    }
    
}
