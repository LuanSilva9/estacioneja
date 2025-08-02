package br.com.estacioneja.usecases.interfaces;

import java.util.List;

import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.dto.input.VinculoDTO;
import br.com.estacioneja.dto.output.VinculoOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IVinculo extends IBase<Vinculo, Long, VinculoDTO, VinculoOutputDTO> {
    // Vinculo findByUsuarioAndEstacionamento(Usuario usuario, Estacionamento estacionamento);

    List<VinculoOutputDTO> findVincleByUserId(Long userId);
}
