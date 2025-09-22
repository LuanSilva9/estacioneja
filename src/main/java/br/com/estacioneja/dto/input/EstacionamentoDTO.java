package br.com.estacioneja.dto.input;

import br.com.estacioneja.domain.enums.Plano;
import br.com.estacioneja.domain.enums.Privacidade;

public record EstacionamentoDTO(Long empresaId, Privacidade privacidade, String descricao, Plano plano, EnderecoDTO endereco, String prefixo, Long capacidade) {
    
}
