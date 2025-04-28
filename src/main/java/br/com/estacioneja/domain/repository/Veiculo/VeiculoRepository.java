package br.com.estacioneja.domain.repository.Veiculo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.estacioneja.domain.model.Veiculo.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, UUID>{
    
}
