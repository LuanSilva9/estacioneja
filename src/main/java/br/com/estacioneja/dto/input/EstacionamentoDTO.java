package br.com.estacioneja.dto.input;

import br.com.estacioneja.domain.model.Estacionamento.StatusEstacionamento;

public record EstacionamentoDTO(Long empresaId,  StatusEstacionamento statusEstacionamento, String prefixo, Long capacidade) {
    
}
