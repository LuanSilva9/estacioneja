package br.com.estacioneja.dto.output;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.estacioneja.domain.model.Solicitacao.Situacao;

public record SolicitacaoOutputDTO(UUID id, UsuarioOutputDTO usuario, EstacionamentoOutputDTO estacionamento, Situacao situacao, LocalDateTime createdAt) {
    
}
