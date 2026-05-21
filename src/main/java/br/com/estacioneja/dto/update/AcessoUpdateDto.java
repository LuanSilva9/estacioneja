package br.com.estacioneja.dto.update;

import br.com.estacioneja.shared.enums.TipoAcesso;
import jakarta.validation.constraints.NotNull;

public record AcessoUpdateDto(
    @NotNull(message = "Tipo de Acesso não pode estar vazio.") TipoAcesso tipoAcesso
) {

}
