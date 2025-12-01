package br.com.estacioneja.dto.input;

import java.util.UUID;

import br.com.estacioneja.domain.enums.TipoAcesso;

public record AcessoDTO(TipoAcesso tipoAcesso, UUID usuarioId) {
    
}
