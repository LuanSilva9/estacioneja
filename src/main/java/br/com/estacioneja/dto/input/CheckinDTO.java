package br.com.estacioneja.dto.input;

import java.time.ZonedDateTime;
import java.util.UUID;

public record CheckinDTO(Long userId, UUID vagaId, String placaVeiculo, ZonedDateTime horarioEntrada, ZonedDateTime horarioSaida) {
    
}
