package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;


import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.dto.output.EnderecoOutputDTO;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    EnderecoOutputDTO toDto(Endereco endereco);
    List<EnderecoOutputDTO> toDtoList(List<Endereco> enderecos);

    Endereco toEntity(EnderecoOutputDTO dto);
    List<Endereco> toEntityList(List<EnderecoOutputDTO> dtoList);
}
