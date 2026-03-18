package br.com.estacioneja.dto.output;

import java.util.UUID;

public record VinculoOutputDTO(UUID id, EstacionamentoOutputDTO estacionamento) {
    
}

