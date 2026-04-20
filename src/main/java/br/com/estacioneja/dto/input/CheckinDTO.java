package br.com.estacioneja.dto.input;

import java.time.ZonedDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CheckinDTO(
    @NotNull(message = "ID do Usuário não pode estar vazio.") Long userId,
    @NotNull(message = "ID da Vaga não pode estar vazio.") UUID vagaId,
    @NotBlank(message = "Placa do Veículo não pode estar vazia.") String placaVeiculo,
    @NotNull(message = "Horário de Entrada não pode estar vazio.") ZonedDateTime horarioEntrada,
    @NotNull(message = "Horário de Saída não pode estar vazio.") ZonedDateTime horarioSaida
) {

}
