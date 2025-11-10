package br.com.estacioneja.dto.input;

import br.com.estacioneja.domain.enums.TipoEmpresa;

public record EmpresaDTO(Long representanteId, String nome, TipoEmpresa tipoEmpresa, String cnpj) {
    
}
