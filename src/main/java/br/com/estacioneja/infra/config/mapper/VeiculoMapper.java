package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.dto.output.VeiculoOutputDTO;

@Mapper(componentModel = "spring")
public interface VeiculoMapper {
    VeiculoOutputDTO toDto(Veiculo veiculo);

    List<VeiculoOutputDTO> toDtoList(List<Veiculo> veiculos);
}
