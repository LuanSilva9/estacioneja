package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.dto.input.VinculoDTO;
import br.com.estacioneja.dto.output.VinculoOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IVinculo extends IBase<Vinculo, UUID, VinculoDTO, VinculoOutputDTO> {
    Boolean existsByEstacionamentoAndProprietarioVeiculo(String placaVeiculo, UUID estacionamentoId);
    Boolean existsByEstacionamentoAndUsuario(Long usuarioId, UUID estacionamentoId);
    
    List<VinculoOutputDTO> findVincleByUserId(Long userId);
}
