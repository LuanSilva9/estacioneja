package br.com.estacioneja.usecases.interfaces;

import java.util.UUID;

import br.com.estacioneja.domain.model.Veiculo.Veiculo;

public interface IVeiculo {
    Veiculo getById(UUID veiculoId);
}
