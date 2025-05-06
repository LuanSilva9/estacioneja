package br.com.estacioneja.domain.repository.Vaga;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Vaga.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, UUID> {
    List<Vaga> findAllByEstacionamento(Estacionamento estacionamento);
    int countByEstacionamento(Estacionamento estacionamento);
    void deleteAllByEstacionamento(Estacionamento estacionamento);
}
