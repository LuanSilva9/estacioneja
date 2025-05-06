package br.com.estacioneja.domain.repository.Veiculo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Veiculo.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, UUID>{
    
}
