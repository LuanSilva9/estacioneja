package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.domain.enums.TipoVeiculo;

public record VeiculoOutputDTO(UUID id, String placa, String modelo, String cor, TipoVeiculo tipoVeiculo, UsuarioOutputDTO usuario) {
    
}
