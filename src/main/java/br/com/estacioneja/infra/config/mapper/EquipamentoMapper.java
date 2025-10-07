package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.estacioneja.domain.model.Equipamento.Equipamento;
import br.com.estacioneja.dto.output.EquipamentoOutputDTO;

@Mapper(componentModel = "spring")
public interface EquipamentoMapper {
    EquipamentoOutputDTO toDto(Equipamento equipamento);

    List<EquipamentoOutputDTO> toDtoList(List<Equipamento> equipamentos);
}
