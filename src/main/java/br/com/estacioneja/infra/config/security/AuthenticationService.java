package br.com.estacioneja.infra.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;

    public AuthenticationService(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario loginAndReturnUser(String email, String senha) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));

        return usuarioRepository.findByEmailWithAcessos(email).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

}
