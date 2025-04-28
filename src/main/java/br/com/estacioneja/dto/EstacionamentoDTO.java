package br.com.estacioneja.dto;

import br.com.estacioneja.domain.model.Estacionamento.StatusEstacionamento;

public record EstacionamentoDTO(Long empresaId, Long capacidade, StatusEstacionamento statusEstacionamento) {
    
}
