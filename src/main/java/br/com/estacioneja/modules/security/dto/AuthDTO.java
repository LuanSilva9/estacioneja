package br.com.estacioneja.modules.security.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(
    @NotBlank(message = "Email não pode estar vazio.") String email,
    @NotBlank(message = "Senha não pode estar vazia.") String senha
) {

}
