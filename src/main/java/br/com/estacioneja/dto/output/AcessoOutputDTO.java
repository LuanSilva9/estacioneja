package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.domain.model.Acesso.TipoAcesso;

public record AcessoOutputDTO(UUID id, TipoAcesso tipoAcesso, UsuarioOutputDTO usuario, EmpresaOutputDTO empresa) {
    
}
