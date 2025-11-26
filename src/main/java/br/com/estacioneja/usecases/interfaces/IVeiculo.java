package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.dto.input.VeiculoDTO;
import br.com.estacioneja.dto.output.VeiculoOutputDTO;

public interface IVeiculo {
    List<Veiculo> findByProprietarioId(Long proprietarioId);
    Veiculo findByPlaca(String placa);
    
    
    /* CRUD */
    VeiculoOutputDTO create(VeiculoDTO dto, Usuario proprietario);
    void update(UUID id, VeiculoDTO dto, Usuario proprietario);
    void delete(UUID id, Usuario proprietario);
    
    /* Consultas */
    Veiculo findEntityById(UUID id, Usuario proprietario);
    VeiculoOutputDTO findById(UUID id, Usuario proprietario);
    
    /* Authorize */
    void authorizeUser(Veiculo veiculo, Usuario proprietario);
}