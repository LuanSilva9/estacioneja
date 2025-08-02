package br.com.estacioneja.dto.output;

import br.com.estacioneja.domain.model.Empresa.TipoEmpresa;

public record EmpresaOutputDTO(Long id, String nome, String endereco, TipoEmpresa tipoEmpresa, String cnpj, String prefixo, UsuarioOutputDTO representante) {
    
}
