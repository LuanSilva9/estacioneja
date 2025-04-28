package br.com.estacioneja.domain.repository.Estacionamento;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;

public interface EstacionamentoRepository extends JpaRepository<Estacionamento, UUID> {
    
}
