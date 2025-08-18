package br.com.estacioneja.dto.output;

import br.com.estacioneja.domain.model.Acesso.TipoAcesso;

public record AcessoOutputDTO(TipoAcesso tipoAcesso, UsuarioOutputDTO usuario, EmpresaOutputDTO empresa) {
    
}
