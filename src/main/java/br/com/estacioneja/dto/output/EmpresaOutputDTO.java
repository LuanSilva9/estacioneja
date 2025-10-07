package br.com.estacioneja.dto.output;

import br.com.estacioneja.domain.model.Empresa.TipoEmpresa;

public record EmpresaOutputDTO(Long id, String nome, TipoEmpresa tipoEmpresa, UsuarioOutputDTO representanteMaster) {
    
}
