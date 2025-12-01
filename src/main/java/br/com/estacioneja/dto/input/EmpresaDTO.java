package br.com.estacioneja.dto.input;

import java.util.UUID;

import br.com.estacioneja.domain.enums.Plano;
import br.com.estacioneja.domain.enums.TipoEmpresa;

public record EmpresaDTO(UUID representanteId, String nome, EnderecoDTO endereco, TipoEmpresa tipoEmpresa, String cnpj, String prefixo, Plano plano, UUID empresaId) {
    
}
