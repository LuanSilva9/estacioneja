package br.com.estacioneja.modules.estacionamento.dto;

import br.com.estacioneja.shared.enums.TipoVeiculo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegraCapacidadeInputDTO(
    @NotNull(message = "Tipo de veículo não pode estar vazio.") TipoVeiculo tipoVeiculo,
    @NotNull(message = "Capacidade não pode estar vazia.") @Positive(message = "Capacidade deve ser maior que zero.") Long capacidade
) { }
