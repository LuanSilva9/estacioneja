package br.com.estacioneja.modules.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUsuarioDto(
    @NotBlank(message = "Nome de Usuário não pode estar vazio.") String name,
    @Email(message = "Email Inválido") @NotBlank(message = "Email não pode estar vazio.") String email,
    @NotBlank(message = "CPF não pode estar vazio.") String cpf,
    @NotBlank(message = "Telefone não pode estar vazio.") String telefone
) { }
