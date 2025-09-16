package br.com.estacioneja.dto.input;

import java.util.UUID;

import br.com.estacioneja.domain.model.Solicitacao.Situacao;

public record SolicitacaoDTO(Long usuarioId, UUID estacionamentoId, Situacao situacao) {
    
}