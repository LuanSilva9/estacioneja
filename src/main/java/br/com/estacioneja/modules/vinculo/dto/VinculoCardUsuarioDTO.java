package br.com.estacioneja.modules.vinculo.dto;

import java.util.UUID;

public record VinculoCardUsuarioDTO(
        UUID id,
        VeiculoResumoDTO veiculo,
        EstacionamentoCardUsuarioDTO estacionamento
) { }
