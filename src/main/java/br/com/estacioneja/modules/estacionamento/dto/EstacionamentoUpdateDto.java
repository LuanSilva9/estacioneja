package br.com.estacioneja.modules.estacionamento.dto;

import java.util.List;

import br.com.estacioneja.shared.enums.MetodoEntrada;
import br.com.estacioneja.shared.enums.Privacidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EstacionamentoUpdateDto(
    @NotNull(message = "Privacidade não pode estar vazia.") Privacidade privacidade,
    @NotBlank(message = "Descrição não pode estar vazia.") String descricao,
    @NotNull(message = "Metodo de entrada não pode estar vazio.") MetodoEntrada metodoEntrada,
    @NotEmpty(message = "Informe ao menos uma regra de capacidade.") @Valid List<RegraCapacidadeInputDTO> regrasCapacidade
) { }
