package br.com.estacioneja.dto.o;

import br.com.estacioneja.domain.model.Estacionamento.StatusEstacionamento;

public record EstacionamentoOutputDTO(Long capacidadeTotal, Long vagasDisponiveis, StatusEstacionamento statusEstacionamento, String prefixo, EmpresaOutputDTO empresa) {

}
