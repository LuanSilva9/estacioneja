package br.com.estacioneja.modules.vinculo.dto;

import java.util.UUID;

import br.com.estacioneja.shared.enums.TipoVeiculo;

public record VeiculoResumoDTO(
        UUID id,
        String placa,
        String modelo,
        String cor,
        TipoVeiculo tipoVeiculo,
        String observacao
) { }
