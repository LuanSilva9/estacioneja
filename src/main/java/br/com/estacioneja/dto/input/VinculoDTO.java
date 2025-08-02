package br.com.estacioneja.dto.input;

import java.util.UUID;

public record VinculoDTO(Long usuarioId, UUID estacionamentoId) {
    
}
