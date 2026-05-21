package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.modules.usuario.dto.ReadUsuarioDto;
import br.com.estacioneja.shared.enums.TipoVeiculo;

public record VeiculoOutputDTO(UUID id, String placa, String modelo, String cor, TipoVeiculo tipoVeiculo, String observacao, ReadUsuarioDto usuario) {
    
}
