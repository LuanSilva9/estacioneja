package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.model.Vaga.Vaga;
import br.com.estacioneja.dto.input.VagaDTO;
import br.com.estacioneja.dto.output.VagaOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IVaga extends IBase<Vaga, UUID, VagaDTO, VagaOutputDTO>{
    List<VagaOutputDTO> findAllByParking(UUID idEstacionamento);
    void deleteAllByParking(UUID estacionamentoId);

    VagaOutputDTO toFree(Vaga vaga);
    VagaOutputDTO toSchedule(Vaga vaga);
    VagaOutputDTO toOccupy(Vaga vaga);
}
