package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.domain.enums.TipoComunicacao;
import br.com.estacioneja.domain.enums.TipoProtocolo;

public record ConexaoOutputDTO(UUID id, TipoComunicacao tipoComunicacao, TipoProtocolo tipoProtocolo, String endereco, Integer porta) {
    
}
