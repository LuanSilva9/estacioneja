package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.dto.input.VinculoDTO;
import br.com.estacioneja.dto.output.VinculoOutputDTO;

public interface IVinculo {
    Boolean existsByEstacionamentoAndVeiculo(Veiculo veiculo, Estacionamento estacionamento);
    
    List<VinculoOutputDTO> findVincleByUser(Usuario usuario);

    // CRUD
    VinculoOutputDTO create(VinculoDTO dto);
    void update(UUID id, VinculoDTO dto);
    void delete(UUID id);

    /* Consultas */
    Vinculo findEntityById(UUID id);
    VinculoOutputDTO findById(UUID id);
    Boolean hasVincle(String placaVeiculo, UUID estacionamentoId);
}
