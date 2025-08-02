package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.estacioneja.domain.model.Vaga.Vaga;
import br.com.estacioneja.dto.output.VagaOutputDTO;

@Mapper(componentModel = "spring")
public interface VagaMapper {
    @Mapping(source = "estacionamento.id", target = "estacionamentoId")
    VagaOutputDTO toDto(Vaga vaga);

    List<VagaOutputDTO> toDtoList(List<Vaga> vagas);
}
