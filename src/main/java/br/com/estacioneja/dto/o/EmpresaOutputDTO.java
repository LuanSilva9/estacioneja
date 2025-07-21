package br.com.estacioneja.dto.o;

import br.com.estacioneja.domain.model.Empresa.TipoEmpresa;

public record EmpresaOutputDTO(String nome, String endereco, TipoEmpresa tipoEmpresa, String cnpj, String prefixo, UsuarioOutputDTO representante) {
    
}
