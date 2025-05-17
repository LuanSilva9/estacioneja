package br.com.estacioneja.dto.i;

import br.com.estacioneja.domain.model.Acesso.TipoAcesso;

public record AcessoDTO(TipoAcesso tipoAcesso, Long usuarioId, Long empresaId) {
    
}
