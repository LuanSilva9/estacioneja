package br.com.estacioneja.modules.vinculo.dto;

import java.util.UUID;

public record VinculoLinhaAdminDTO(
        UUID id,
        VeiculoResumoDTO veiculo,
        ProprietarioResumoDTO proprietario,
        EstacionamentoRotuloDTO estacionamento
) { }
