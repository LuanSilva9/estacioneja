package br.com.estacioneja.modules.usuario.dto;

import br.com.estacioneja.shared.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUsuarioDto(
    @NotBlank(message = "Nome de Usuario não pode estar vazio.") String name,
    @NotBlank(message = "CPF não pode estar vazio.") String cpf,
    @NotBlank(message = "Telefone não pode estar vazio.") String telefone,
    @Email(message = "Email inválido") @NotBlank(message = "Email não pode estar vazio.") String email,
    @Size(min = 6, message = "Senha deve ter mais de 6 caracteres") @NotBlank(message = "Senha não pode estar vazia") String senha,
    @NotNull(message = "Tipo de Usuário não pode ser nulo") TipoUsuario tipoUsuario
) { }
