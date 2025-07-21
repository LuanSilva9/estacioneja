package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.o.UsuarioOutputDTO;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioOutputDTO toDto(Usuario usuario);

    List<UsuarioOutputDTO> toDtoList(List<Usuario> usuarios);

}
