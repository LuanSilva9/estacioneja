package br.com.estacioneja.dto.input;

import br.com.estacioneja.domain.enums.TipoAcesso;

public record AcessoDTO(TipoAcesso tipoAcesso, Long usuarioId) {
    
}
