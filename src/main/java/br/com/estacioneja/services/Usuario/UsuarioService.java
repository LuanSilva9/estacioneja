package br.com.estacioneja.services.Usuario;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.input.UsuarioDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.dto.update.UsuarioUpdateDto;
import br.com.estacioneja.exceptions.custom.DuplicateException;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.UsuarioMapper;
import br.com.estacioneja.usecases.interfaces.IUsuario;


@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuario {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    /* TRANSACOES */

    @Override @Transactional
    public UsuarioOutputDTO create(UsuarioDTO dto) {
        if(isEmailInUse(dto.email(), null)) 
            throw new DuplicateException("Email já está sendo Usado");
        
        if(isCpfInUse(dto.cpf(), null)) 
            throw new DuplicateException("CPF já está sendo Usado");

        Usuario newUsuario = new Usuario(dto.name(), dto.email(), dto.cpf(), dto.telefone(), dto.tipoUsuario());

        newUsuario.setSenha(passwordEncoder.encode(dto.senha()));

        this.usuarioRepository.save(newUsuario);

        return usuarioMapper.toDto(newUsuario);
    }

    @Override @Transactional
    public void update(UUID id, UsuarioUpdateDto dto) {
        Usuario usuario = findEntityById(id);
        
        if(isEmailInUse(dto.email(), id)) 
            throw new DuplicateException("Email já está sendo usado");
        if(isCpfInUse(dto.cpf(), id)) 
            throw new DuplicateException("CPF já está sendo usado");
        

        usuario.updateData(dto.name(), dto.telefone(), dto.cpf(), dto.email());
    }


    @Override @Transactional
    public void delete(UUID id) {
        Usuario usuario = findEntityById(id);

        usuarioRepository.delete(usuario);
    }

    @Override @Transactional(readOnly=true)
    public UsuarioOutputDTO findById(UUID id) {
        return usuarioMapper.toDto(findEntityById(id));
    }

    @Override @Transactional(readOnly = true)
    public Usuario findEntityById(UUID id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
    }

    
    /* VALIDAÇÕES */
    private boolean isEmailInUse(String email, UUID id) {
        return (id == null) ? usuarioRepository.existsByEmail(email) : usuarioRepository.existsByEmailAndIdNot(email, id);
    }

    private boolean isCpfInUse(String cpf, UUID id) {
        return (id == null) ? usuarioRepository.existsByCpf(cpf) : usuarioRepository.existsByCpfAndIdNot(cpf, id);
    }
}
