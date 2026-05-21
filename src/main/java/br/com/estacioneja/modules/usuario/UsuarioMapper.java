package br.com.estacioneja.modules.usuario;

import org.springframework.stereotype.Component;

import br.com.estacioneja.modules.usuario.dto.ReadUsuarioDto;
import br.com.estacioneja.shared.mapper.AbstractMapper;

@Component
public class UsuarioMapper extends AbstractMapper<Usuario, ReadUsuarioDto> {

    @Override
    public ReadUsuarioDto toDto(Usuario usuario) {
        if (usuario == null) return null;
        return new ReadUsuarioDto(
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
