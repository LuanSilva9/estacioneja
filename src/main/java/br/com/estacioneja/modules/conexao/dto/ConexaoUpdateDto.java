package br.com.estacioneja.modules.conexao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConexaoUpdateDto(
    @NotBlank(message = "Endereço não pode estar vazio.") String endereco,
    @NotNull(message = "Porta não pode estar vazia.") Integer porta,
    @NotBlank(message = "Credenciais não podem estar vazias.") String credenciais
) { }
