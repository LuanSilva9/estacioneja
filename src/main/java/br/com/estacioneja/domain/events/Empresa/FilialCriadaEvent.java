package br.com.estacioneja.domain.events.Empresa;

import java.util.UUID;

public record FilialCriadaEvent(UUID filialId, Long representanteId) {
    
}
