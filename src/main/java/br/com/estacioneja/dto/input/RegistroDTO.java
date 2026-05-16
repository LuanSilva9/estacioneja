package br.com.estacioneja.dto.input;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroDTO(
    @NotBlank(message = "Placa não pode estar vazia.") String placa,
    @NotNull(message = "ID do Estacionamento não pode estar vazio.") UUID estacionamentoId
) {

}
