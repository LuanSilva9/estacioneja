package br.com.estacioneja.domain.events.Empresa;

import java.util.UUID;

public record EmpresaCriadaEvent(UUID empresaId, UUID representanteId) {
    
}
