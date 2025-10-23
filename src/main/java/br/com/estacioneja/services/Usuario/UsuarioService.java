package br.com.estacioneja.services.Usuario;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.input.UsuarioDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.exceptions.custom.DuplicateUserException;
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

    /* TRANSACOES */

    @Override @Transactional
    public UsuarioOutputDTO create(UsuarioDTO dto) {
        existsEmailOrCpf(dto.email(), dto.cpf());

        Usuario newUsuario = new Usuario(dto);

        this.usuarioRepository.save(newUsuario);

        return usuarioMapper.toDto(newUsuario);
    }

    @Override @Transactional 
    public UsuarioOutputDTO update(Long id, UsuarioDTO dto) {
        Usuario usuario = findEntityById(id);

        usuario.setName(dto.name());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());

        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    @Override @Transactional
    public void delete(Long id) {
        Usuario usuario = findEntityById(id);

        usuarioRepository.delete(usuario);
    }

    /* CONSULTAS */

    @Override
    public UsuarioOutputDTO findById(Long id) {
        return usuarioMapper.toDto(findEntityById(id));
    }

    @Override
    public Usuario findEntityById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void existsEmailOrCpf(String email, String cpf) {
        if(this.usuarioRepository.existsByCpf(cpf) || this.usuarioRepository.existsByEmail(email)) throw new DuplicateUserException();
    }

    /* Mappers */

    public UsuarioOutputDTO toDto(Usuario usuario) {
        return usuarioMapper.toDto(usuario);
    }

    public List<UsuarioOutputDTO> toDtoList(List<Usuario> usuarios) {
        return usuarioMapper.toDtoList(usuarios);
    }
}
