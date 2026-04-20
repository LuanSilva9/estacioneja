package br.com.estacioneja.dto.input;

import br.com.estacioneja.domain.enums.TipoAcesso;
import jakarta.validation.constraints.NotNull;

public record TipoAcessoDTO(
    @NotNull(message = "Tipo de Acesso não pode estar vazio.") TipoAcesso tipoAcesso
) {

}
