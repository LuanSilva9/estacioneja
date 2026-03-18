package br.com.estacioneja.dto.input;

import br.com.estacioneja.domain.enums.TipoVeiculo;

public record VeiculoDTO(String placa, String cor, String modelo, TipoVeiculo tipoVeiculo, String observacao) {
    
}
