package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.estacioneja.domain.model.Registro.Registro;
import br.com.estacioneja.dto.output.RegistroOutputDTO;

@Mapper(componentModel = "spring")
public interface RegistroMapper {
    RegistroOutputDTO toDto(Registro registro);
    List<RegistroOutputDTO> toDtoList(List<Registro> registro);
}
