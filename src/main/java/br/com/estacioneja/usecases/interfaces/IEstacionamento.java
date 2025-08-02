package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.dto.input.EstacionamentoDTO;
import br.com.estacioneja.dto.output.EstacionamentoOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IEstacionamento extends IBase<Estacionamento, UUID, EstacionamentoDTO, EstacionamentoOutputDTO> {
    Long getAvaliableVacancies(UUID idEstacionamento); 
    Long getTotalVacancies(UUID idEstacionamento); 

    List<EstacionamentoOutputDTO> findEstacionamentoByEmpresa(Long idEmpresa);
}
