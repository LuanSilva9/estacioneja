package br.com.estacioneja.services.Usuario;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.enums.TipoUsuario;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.input.UsuarioDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.dto.update.UsuarioUpdateDto;
import br.com.estacioneja.exceptions.custom.DuplicateException;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.UsuarioMapper;
import br.com.estacioneja.usecases.interfaces.IUsuario;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService implements IUsuario {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /* TRANSACOES */

    @Override @Transactional
    public UsuarioOutputDTO create(UsuarioDTO dto) {
        existsEmailOrCpf(dto.email(), dto.cpf(), dto.tipoUsuario());

        Usuario newUsuario = new Usuario(dto);

        newUsuario.setSenha(passwordEncoder.encode(dto.senha()));

        this.usuarioRepository.save(newUsuario);

        return usuarioMapper.toDto(newUsuario);
    }

    @Transactional
    public void update(UUID id, UsuarioUpdateDto dto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (dto.name() != null && !dto.name().isBlank()) {
            usuario.setName(dto.name().trim());
        }

        if (dto.telefone() != null && !dto.telefone().isBlank()) {
            usuario.setTelefone(dto.telefone().trim());
        }

        if(dto.cpf() != null && !dto.cpf().isBlank()) {
            usuario.setCpf(dto.cpf());
        }

        if (dto.email() != null && !dto.email().isBlank()) {

            boolean emailJaExiste = usuarioRepository.existsByEmailAndIdNot(dto.email().trim(), usuario.getId());

            if (emailJaExiste) {
                throw new DuplicateException("E-mail já está em uso");
            }

            usuario.setEmail(dto.email().trim());
        }


        usuarioRepository.save(usuario);
    }


    @Override @Transactional
    public void delete(UUID id) {
        Usuario usuario = findEntityById(id);

        usuarioRepository.delete(usuario);
    }

    @Override
    public UsuarioOutputDTO findById(UUID id) {
        return usuarioMapper.toDto(findEntityById(id));
    }

    @Override
    public Usuario findEntityById(UUID id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
    }

    @Override
    public void existsEmailOrCpf(String email, String cpf, TipoUsuario tipoUsuario) {
        if(this.usuarioRepository.existsByCpfAndTipoUsuario(cpf, tipoUsuario) || this.usuarioRepository.existsByEmailAndTipoUsuario(email, tipoUsuario)) throw new DuplicateException();
    }

    /* MAPPERS */

    public UsuarioOutputDTO toDto(Usuario usuario) {
        return usuarioMapper.toDto(usuario);
    }

    public List<UsuarioOutputDTO> toDtoList(List<Usuario> usuarios) {
        return usuarioMapper.toDtoList(usuarios);
    }
}
