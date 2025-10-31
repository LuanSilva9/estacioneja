package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.domain.enums.Plano;

public record FilialOutputDTO(UUID id, String nome, EnderecoOutputDTO endereco, Plano plano, String cnpj, UsuarioOutputDTO representante, EmpresaOutputDTO empresa) {
    
}
 