package br.com.estacioneja.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoUpdateDto(
    @NotBlank(message = "Logradouro não pode estar vazio.") String logradouro,
    @NotBlank(message = "Bairro não pode estar vazio.") String bairro,
    @NotBlank(message = "Cidade não pode estar vazia.") String cidade,
    @NotBlank(message = "UF não pode estar vazia.") String uf,
    @NotBlank(message = "CEP não pode estar vazio.") String cep,
    @NotNull(message = "Latitude não pode estar vazia.") Double latitude,
    @NotNull(message = "Longitude não pode estar vazia.") Double longitude
) {

}
