package br.com.estacioneja.services.Usuario;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.input.UsuarioDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.exceptions.custom.UserNotFoundException;
import br.com.estacioneja.infra.config.mapper.UsuarioMapper;
import br.com.estacioneja.usecases.interfaces.IUsuario;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService implements IUsuario {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override @Transactional
    public UsuarioOutputDTO create(UsuarioDTO dto) {
        Usuario newUsuario = new Usuario(dto);

        this.usuarioRepository.save(newUsuario);

        return usuarioMapper.toDto(newUsuario);
    }

    @Override @Transactional 
    public UsuarioOutputDTO update(Long id, UsuarioDTO dto) {
        Usuario usuario = findEntityById(id);

        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());
        usuario.setName(dto.senha());

        // Esse método é um pouco mais sensivel então qnd  formos apresentar uma versão mais madura do SaaS teremos que validar algumas coisas a mais e integrar com sistema de mandar email
        
        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    @Override @Transactional
    public void delete(Long id) {
        Usuario usuario = findEntityById(id);

        usuarioRepository.delete(usuario);
    }

    @Override @Transactional
    public UsuarioOutputDTO findById(Long id) {
        return usuarioMapper.toDto(findEntityById(id));
    }

    @Override @Transactional
    public Usuario findEntityById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
