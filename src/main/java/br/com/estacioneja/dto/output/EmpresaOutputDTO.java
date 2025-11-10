package br.com.estacioneja.dto.output;

import br.com.estacioneja.domain.enums.TipoEmpresa;

public record EmpresaOutputDTO(Long id, String nome, TipoEmpresa tipoEmpresa, UsuarioOutputDTO representanteMaster) {
    
}
