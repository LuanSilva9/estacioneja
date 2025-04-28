package br.com.estacioneja.domain.repository.Vaga;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.estacioneja.domain.model.Vaga.Vaga;

public interface VagaRepository extends JpaRepository<Vaga, UUID> {
    
}
