package br.com.estacioneja.usecases.interfaces;

import java.util.UUID;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.dto.input.EstacionamentoDTO;
import br.com.estacioneja.dto.output.EstacionamentoOutputDTO;

public interface IEstacionamento {
    /* CRUD */
    EstacionamentoOutputDTO create(EstacionamentoDTO dto);
    void update(UUID id, EstacionamentoDTO dto);
    void delete(UUID id);

    /* Consultas */
    Estacionamento findEntityById(UUID id);
    EstacionamentoOutputDTO findById(UUID id);
}
