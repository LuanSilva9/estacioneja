package br.com.estacioneja.dto.input;

import br.com.estacioneja.shared.enums.TipoVeiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VeiculoDTO(
    @NotBlank(message = "Placa não pode estar vazia.") String placa,
    @NotBlank(message = "Cor não pode estar vazia.") String cor,
    @NotBlank(message = "Modelo não pode estar vazio.") String modelo,
    @NotNull(message = "Tipo de Veículo não pode estar vazio.") TipoVeiculo tipoVeiculo,
    String observacao
) {

}
