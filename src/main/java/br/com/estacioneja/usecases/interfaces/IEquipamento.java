package br.com.estacioneja.usecases.interfaces;

import java.util.UUID;

import br.com.estacioneja.domain.model.Equipamento.Equipamento;
import br.com.estacioneja.dto.input.EquipamentoDTO;
import br.com.estacioneja.dto.output.EquipamentoOutputDTO;

public interface IEquipamento {
    /* CRUD */
    EquipamentoOutputDTO create(EquipamentoDTO dto);
    void update(UUID id, EquipamentoDTO dto);
    void delete(UUID id);

    /* Consultas */
    Equipamento findEntityById(UUID id);
    EquipamentoOutputDTO findById(UUID id);
}
