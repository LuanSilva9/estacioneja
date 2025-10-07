package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.dto.output.EstacionamentoOutputDTO;

@Mapper(componentModel = "spring")
public interface EstacionamentoMapper {
    EstacionamentoOutputDTO toDto(Estacionamento estacionamento);

    List<EstacionamentoOutputDTO> toDtoList(List<Estacionamento> estacionamentos);

}
 