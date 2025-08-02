package br.com.estacioneja.dto.input;

import java.util.UUID;

import br.com.estacioneja.domain.model.Vaga.TipoVaga;

public record VagaDTO(UUID estacionamentoId, TipoVaga tipoVaga, String slug) {
    
}
