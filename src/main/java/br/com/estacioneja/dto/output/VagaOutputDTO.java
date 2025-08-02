package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.domain.model.Vaga.StatusVaga;
import br.com.estacioneja.domain.model.Vaga.TipoVaga;

public record VagaOutputDTO(UUID id, TipoVaga tipoVaga, String slug, UUID estacionamentoId, StatusVaga statusVaga) {}