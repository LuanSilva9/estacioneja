package br.com.estacioneja.dto;

import br.com.estacioneja.domain.model.Veiculo.TipoVeiculo;

public record VeiculoDTO(Long proprietarioId, String modelo, String cor, String placa, TipoVeiculo tipoVeiculo) {
    
}
