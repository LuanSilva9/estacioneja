package br.com.estacioneja.dto.input;

import br.com.estacioneja.domain.enums.TipoComunicacao;
import br.com.estacioneja.domain.enums.TipoProtocolo;

public record ConexaoDTO(TipoComunicacao tipoComunicacao, TipoProtocolo tipoProtocolo, String endereco, Integer porta, String credenciais, String enderecoMac) {
    
}
