package br.com.estacioneja.domain.repository.Equipamento;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Equipamento.Equipamento;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, UUID> {
    
}
