package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.domain.model.Estacionamento.StatusEstacionamento;

public record EstacionamentoOutputDTO(UUID id, StatusEstacionamento statusEstacionamento, String prefixo, EmpresaOutputDTO empresa) {

}
