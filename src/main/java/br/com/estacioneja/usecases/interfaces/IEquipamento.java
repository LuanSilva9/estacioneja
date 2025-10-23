package br.com.estacioneja.usecases.interfaces;

import java.util.UUID;

import br.com.estacioneja.domain.model.Equipamento.Equipamento;
import br.com.estacioneja.dto.input.EquipamentoDTO;
import br.com.estacioneja.dto.output.EquipamentoOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IEquipamento extends IBase<Equipamento, UUID, EquipamentoDTO, EquipamentoOutputDTO> {
    
}
