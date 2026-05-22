package br.com.estacioneja.modules.acessos.dto;

import java.util.UUID;

import br.com.estacioneja.shared.enums.TipoAcesso;
import jakarta.validation.constraints.NotNull;

public record AcessoDTO(
    @NotNull(message = "Tipo de Acesso não pode estar vazio.") TipoAcesso tipoAcesso,
    @NotNull(message = "ID do Usuário não pode estar vazio.") UUID usuarioId
) {

}
