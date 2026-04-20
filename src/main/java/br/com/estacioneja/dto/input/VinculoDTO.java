package br.com.estacioneja.dto.input;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VinculoDTO(
    @NotNull(message = "ID do Estacionamento não pode estar vazio.") UUID estacionamentoId,
    @NotBlank(message = "Placa do Veículo não pode estar vazia.") String placaVeiculo
) {

}
