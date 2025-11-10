package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.domain.enums.TipoAcesso;

public record AcessoOutputDTO(UUID id, TipoAcesso tipoAcesso, UsuarioOutputDTO usuario, EmpresaOutputDTO empresa) {
    
}
