package br.com.estacioneja.dto.input;

import br.com.estacioneja.domain.model.Empresa.TipoEmpresa;

public record EmpresaDTO(Long representanteId, String nome, String endereco, TipoEmpresa tipoEmpresa, String cnpj, String prefixo) {
    
}
