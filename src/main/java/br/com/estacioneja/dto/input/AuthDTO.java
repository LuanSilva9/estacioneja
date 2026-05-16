package br.com.estacioneja.dto.input;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(
    @NotBlank(message = "Email não pode estar vazio.") String email,
    @NotBlank(message = "Senha não pode estar vazia.") String senha
) {

}
