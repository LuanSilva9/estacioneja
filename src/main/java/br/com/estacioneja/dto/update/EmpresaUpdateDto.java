package br.com.estacioneja.dto.update;

import br.com.estacioneja.domain.enums.TipoEmpresa;

public record EmpresaUpdateDto(String nome, TipoEmpresa tipoEmpresa) {
    
}
