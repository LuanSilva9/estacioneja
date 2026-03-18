package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.enums.Privacidade;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.dto.input.EstacionamentoDTO;
import br.com.estacioneja.dto.output.EstacionamentoOutputDTO;

public interface IEstacionamento {
    /* CRUD */
    EstacionamentoOutputDTO create(EstacionamentoDTO dto);
    void update(UUID id, EstacionamentoDTO dto);
    void delete(UUID id);

    /* Consultas */
    List<EstacionamentoOutputDTO> findByPrivacidade(Privacidade privacidade);
    List<EstacionamentoOutputDTO> findByEmpresa(UUID empresaId);
    Estacionamento findEntityById(UUID id);
    EstacionamentoOutputDTO findById(UUID id);
}
