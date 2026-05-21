package br.com.estacioneja.dto.update;

import br.com.estacioneja.shared.enums.Privacidade;
import jakarta.validation.constraints.NotNull;

public record EstacionamentoUpdateDto(
    @NotNull(message = "Privacidade não pode estar vazia.") Privacidade privacidade
) {

}
