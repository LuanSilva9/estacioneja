package br.com.estacioneja.dto.input;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.enums.Privacidade;
import br.com.estacioneja.domain.enums.TipoVeiculo;

public record EstacionamentoDTO(Privacidade privacidade, String descricao, UUID filialId,  List<TipoVeiculo> regraEstacionamento, Long capacidade) {
    
}
