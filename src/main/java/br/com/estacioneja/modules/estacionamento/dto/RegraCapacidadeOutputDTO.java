package br.com.estacioneja.modules.estacionamento.dto;

import br.com.estacioneja.shared.enums.TipoVeiculo;

public record RegraCapacidadeOutputDTO(
    TipoVeiculo tipoVeiculo,
    Long capacidade,
    Long capacidadeDisponivel
) { }
