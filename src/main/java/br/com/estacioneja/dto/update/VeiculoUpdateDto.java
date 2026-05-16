package br.com.estacioneja.dto.update;

import br.com.estacioneja.domain.enums.TipoVeiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VeiculoUpdateDto(
    @NotBlank(message = "Modelo não pode estar vazio.") String modelo,
    @NotBlank(message = "Cor não pode estar vazia.") String cor,
    @NotNull(message = "Tipo de Veículo não pode estar vazio.") TipoVeiculo tipoVeiculo
) {

}
