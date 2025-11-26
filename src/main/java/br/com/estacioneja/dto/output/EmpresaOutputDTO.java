package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.domain.enums.Plano;
import br.com.estacioneja.domain.enums.TipoEmpresa;

public record EmpresaOutputDTO(UUID id, String nome, TipoEmpresa tipoEmpresa, UsuarioOutputDTO representante, String cnpj, String prefixo, Plano plano, EnderecoOutputDTO endereco) {
    
}
