package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.estacioneja.domain.model.Solicitacao.Solicitacao;
import br.com.estacioneja.dto.output.SolicitacaoOutputDTO;

@Mapper(componentModel = "spring")
public interface SolicitacaoMapper {
    SolicitacaoOutputDTO toDto(Solicitacao solicitacao);
 
    List<SolicitacaoOutputDTO> toDtoList(List<Solicitacao> solicitacoes);

}