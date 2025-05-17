package br.com.estacioneja.dto.i;

import br.com.estacioneja.domain.model.Estacionamento.StatusEstacionamento;

public record EstacionamentoDTO(Long empresaId, Long capacidade, StatusEstacionamento statusEstacionamento, String prefixo) {
    
}
