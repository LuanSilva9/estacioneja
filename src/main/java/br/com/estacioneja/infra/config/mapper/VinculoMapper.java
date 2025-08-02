package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.dto.output.VinculoOutputDTO;

@Mapper(componentModel = "spring")
public interface VinculoMapper {
    @Mapping(source = "estacionamento.id", target = "estacionamentoId")
    @Mapping(source = "usuario.id", target = "usuarioId")
    VinculoOutputDTO toDto(Vinculo vinculo);

    List<VinculoOutputDTO> toDtoList(List<Vinculo> vinculos);
}
