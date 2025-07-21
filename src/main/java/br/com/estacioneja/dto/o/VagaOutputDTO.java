package br.com.estacioneja.dto.o;

import java.util.UUID;

import br.com.estacioneja.domain.model.Vaga.StatusVaga;
import br.com.estacioneja.domain.model.Vaga.TipoVaga;

public record VagaOutputDTO(TipoVaga tipoVaga, String slug, UUID estacionamentoId, StatusVaga statusVaga) {}