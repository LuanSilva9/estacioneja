package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.estacioneja.domain.model.Filial.Filial;
import br.com.estacioneja.dto.output.FilialOutputDTO;

@Mapper(componentModel = "spring")
public interface FilialMapper {
    FilialOutputDTO toDto(Filial filial);
    List<FilialOutputDTO> toDtoList(List<Filial> filiais);
}   