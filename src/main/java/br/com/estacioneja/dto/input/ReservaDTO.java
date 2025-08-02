package br.com.estacioneja.dto.input;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ReservaDTO(Long usuarioId, UUID vagaId, UUID veiculoId, ZonedDateTime horarioEntrada, ZonedDateTime horarioSaida) {
    
}
