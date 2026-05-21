package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.shared.enums.TipoComunicacao;
import br.com.estacioneja.shared.enums.TipoProtocolo;

public record ConexaoOutputDTO(UUID id, TipoComunicacao tipoComunicacao, TipoProtocolo tipoProtocolo, String endereco, Integer porta) {
    
}
