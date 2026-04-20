package br.com.estacioneja.dto.input;

import java.time.ZonedDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ReservaDTO(
    @NotNull(message = "ID do Usuário não pode estar vazio.") Long usuarioId,
    @NotNull(message = "ID da Vaga não pode estar vazio.") UUID vagaId,
    @NotNull(message = "ID do Veículo não pode estar vazio.") UUID veiculoId,
    @NotNull(message = "Horário de Entrada não pode estar vazio.") ZonedDateTime horarioEntrada,
    @NotNull(message = "Horário de Saída não pode estar vazio.") ZonedDateTime horarioSaida
) {

}
