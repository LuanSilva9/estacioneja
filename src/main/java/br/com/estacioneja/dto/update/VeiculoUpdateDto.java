package br.com.estacioneja.dto.update;

import br.com.estacioneja.domain.enums.TipoVeiculo;

public record VeiculoUpdateDto(String modelo, String cor, TipoVeiculo tipoVeiculo) {
    
}
