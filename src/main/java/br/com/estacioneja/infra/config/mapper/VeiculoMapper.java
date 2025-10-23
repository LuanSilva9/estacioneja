package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.dto.output.VeiculoOutputDTO;

@Mapper(componentModel = "spring")
public interface VeiculoMapper {
    VeiculoOutputDTO toDto(Veiculo veiculo);
    Veiculo toEntity(VeiculoOutputDTO veiculoOutputDTO);

    List<VeiculoOutputDTO> toDtoList(List<Veiculo> veiculos);
    List<Veiculo> toEntityList(List<VeiculoOutputDTO> veiculoOutputDTO);
}
