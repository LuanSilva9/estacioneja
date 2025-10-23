package br.com.estacioneja.dto.input;

import br.com.estacioneja.domain.enums.Plano;

public record FilialDTO(String nome, EnderecoDTO endereco, String cnpj, String prefixo, Plano plano, Long empresaId, Long representanteId) {
    
}
