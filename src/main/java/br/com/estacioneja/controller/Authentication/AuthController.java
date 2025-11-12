package br.com.estacioneja.controller.Authentication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.dto.input.AuthDTO;
import br.com.estacioneja.dto.input.UsuarioDTO;
import br.com.estacioneja.dto.output.JWTOutputDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.infra.config.security.AuthenticationService;
import br.com.estacioneja.services.Usuario.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthenticationService authService;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationService authService, UsuarioService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTOutputDTO> login(@RequestBody AuthDTO dto) {
        String token = authService.login(dto.email(), dto.senha());
        
        return ResponseEntity.ok().body(new JWTOutputDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioDTO dto) {
        UsuarioOutputDTO criado = usuarioService.create(dto);
        
        return ResponseEntity.ok().build();
    }
    
    

}
