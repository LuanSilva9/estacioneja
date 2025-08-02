package br.com.estacioneja.services.Veiculo;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.domain.repository.Veiculo.VeiculoRepository;
import br.com.estacioneja.exceptions.custom.VeicleNotFoundException;
import br.com.estacioneja.usecases.interfaces.IVeiculo;

@Service
public class VeiculoService implements IVeiculo {
    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @Override
    public Veiculo getById(UUID veiculoId) {
        return veiculoRepository.findById(veiculoId).orElseThrow(VeicleNotFoundException::new);
    }
    
}
