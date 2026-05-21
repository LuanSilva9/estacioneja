package br.com.estacioneja.dto.input;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.shared.enums.Privacidade;
import br.com.estacioneja.shared.enums.TipoVeiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EstacionamentoDTO(
    @NotNull(message = "ID da Empresa não pode estar vazio.") UUID empresaId,
    @NotNull(message = "Privacidade não pode estar vazia.") Privacidade privacidade,
    @NotBlank(message = "Descrição não pode estar vazia.") String descricao,
    @NotNull(message = "Regra de Estacionamento não pode estar vazia.") List<TipoVeiculo> regraEstacionamento,
    @NotNull(message = "Capacidade não pode estar vazia.") Long capacidade
) {

}
