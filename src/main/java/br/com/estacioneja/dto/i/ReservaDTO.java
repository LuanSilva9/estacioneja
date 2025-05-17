package br.com.estacioneja.dto.i;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ReservaDTO(Long usuarioId, UUID vagaId, ZonedDateTime horarioEntrada, ZonedDateTime horarioSaida) {
    
}
