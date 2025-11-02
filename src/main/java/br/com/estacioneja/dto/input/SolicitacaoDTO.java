package br.com.estacioneja.dto.input;

import java.util.UUID;

public record SolicitacaoDTO(Long usuarioId, UUID estacionamentoId, UUID veiculoId) {
    
}