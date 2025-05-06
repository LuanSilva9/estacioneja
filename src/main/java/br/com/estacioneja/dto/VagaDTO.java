package br.com.estacioneja.dto;

import java.util.UUID;

import br.com.estacioneja.domain.model.Vaga.TipoVaga;

public record VagaDTO(UUID estacionamentoId, TipoVaga tipoVaga, String slug) {
    
}
