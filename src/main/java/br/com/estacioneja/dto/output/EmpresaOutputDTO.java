package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.modules.usuario.dto.ReadUsuarioDto;
import br.com.estacioneja.shared.enums.Plano;
import br.com.estacioneja.shared.enums.TipoEmpresa;

public record EmpresaOutputDTO(UUID id, String nome, TipoEmpresa tipoEmpresa, ReadUsuarioDto representante, String cnpj, String prefixo, Plano plano, EnderecoOutputDTO endereco) {
    
}
