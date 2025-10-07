package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.dto.input.VeiculoDTO;
import br.com.estacioneja.dto.output.VeiculoOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IVeiculo extends IBase<Veiculo, UUID, VeiculoDTO, VeiculoOutputDTO>{
    List<Veiculo> findByProprietarioId(Long proprietarioId);
}
