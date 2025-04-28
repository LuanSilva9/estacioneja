package br.com.estacioneja.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ReservaDTO(Long usuarioId, UUID vagaId, ZonedDateTime horarioEntrada, ZonedDateTime horarioSaida) {
    
}
