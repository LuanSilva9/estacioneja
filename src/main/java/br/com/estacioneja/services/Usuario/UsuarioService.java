package br.com.estacioneja.services.Usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.UsuarioDTO;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario createUsuario(UsuarioDTO dto) {
        Usuario newUsuario = new Usuario(dto);

        return this.usuarioRepository.save(newUsuario);
    }

    public List<Usuario> listUsers() {
        return usuarioRepository.findAll();
    }
}
