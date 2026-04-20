package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.dto.input.VeiculoDTO;
import br.com.estacioneja.dto.output.VeiculoOutputDTO;
import br.com.estacioneja.dto.update.VeiculoUpdateDto;

public interface IVeiculo {
    List<VeiculoOutputDTO> findByProprietarioId(UUID proprietarioId);
    Veiculo findByPlaca(String placa);
    
    
    /* CRUD */
    VeiculoOutputDTO create(VeiculoDTO dto, Usuario proprietario);
    void update(UUID id, VeiculoUpdateDto dto, Usuario proprietario);
    void delete(UUID id, Usuario proprietario);
    
    /* Consultas */
    Veiculo findEntityById(UUID id, Usuario proprietario);
    VeiculoOutputDTO findById(UUID id, Usuario proprietario);
}