package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.estacioneja.domain.model.Conexao.Conexao;
import br.com.estacioneja.dto.output.ConexaoOutputDTO;

@Mapper(componentModel = "spring")
public interface ConexaoMapper {
    ConexaoOutputDTO toDto(Conexao conexao);

    List<ConexaoOutputDTO> toDtoList(List<Conexao> conexoes);
}
