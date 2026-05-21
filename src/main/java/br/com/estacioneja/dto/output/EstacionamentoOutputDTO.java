package br.com.estacioneja.dto.output;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.shared.enums.Privacidade;
import br.com.estacioneja.shared.enums.TipoVeiculo;


public record EstacionamentoOutputDTO(UUID id, String descricao, Privacidade privacidade, Long capacidade, Long capacidadeDisponivel, EmpresaOutputDTO empresa, List<TipoVeiculo> regraEstacionamento) {

}
