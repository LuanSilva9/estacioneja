package br.com.estacioneja.infra.config.mapper;

import org.springframework.stereotype.Component;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;

@Component
public class UsuarioMapper extends AbstractMapper<Usuario, UsuarioOutputDTO> {

    @Override
    public UsuarioOutputDTO toDto(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioOutputDTO(
                usuario.getId(),
                usuario.getName(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getEmail(),
                usuario.getTipoUsuario(),
                usuario.getFotoPerfilKey() != null && !usuario.getFotoPerfilKey().isBlank()
        );
    }
}
