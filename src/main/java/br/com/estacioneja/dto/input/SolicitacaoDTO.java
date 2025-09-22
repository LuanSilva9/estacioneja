package br.com.estacioneja.dto.input;

import java.util.UUID;

import br.com.estacioneja.domain.enums.Situacao;

public record SolicitacaoDTO(Long usuarioId, UUID estacionamentoId, Situacao situacao) {
    
}