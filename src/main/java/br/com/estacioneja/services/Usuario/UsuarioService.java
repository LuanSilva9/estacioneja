package br.com.estacioneja.services.Usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.i.UsuarioDTO;
import br.com.estacioneja.dto.o.UsuarioOutputDTO;
import br.com.estacioneja.infra.config.mapper.UsuarioMapper;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Transactional
    public Usuario createUsuario(UsuarioDTO dto) {
        Usuario newUsuario = new Usuario(dto);

        return this.usuarioRepository.save(newUsuario);
    }

    @Transactional
    public List<UsuarioOutputDTO> listUsers() {
        return usuarioMapper.toDtoList(usuarioRepository.findAll());
    }

    @Transactional
    public Usuario getUserById(Long id) throws Exception {
        return usuarioRepository.findById(id).orElseThrow(() -> new Exception("Usuario não encontrado"));
    }

    @Transactional
    public Usuario deleteUsuario(Long id) throws Exception {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new Exception("Usuario não encontrado"));

        usuarioRepository.delete(usuario);

        return usuario;
    }
}
