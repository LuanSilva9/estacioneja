package br.com.estacioneja.dto;

import br.com.estacioneja.domain.model.Empresa.TipoEmpresa;

public record EmpresaDTO(Long representanteId, String endereco, TipoEmpresa tipoEmpresa, String cnpj) {
    
}
