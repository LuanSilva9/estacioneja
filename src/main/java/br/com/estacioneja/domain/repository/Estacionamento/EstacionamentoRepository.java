package br.com.estacioneja.domain.repository.Estacionamento;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import jakarta.persistence.LockModeType;
import br.com.estacioneja.shared.enums.Privacidade;
import java.util.List;
import java.util.Optional;


@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, UUID> {
    List<Estacionamento> findByPrivacidade(Privacidade privacidade);
    List<Estacionamento> findAllByEmpresa(Empresa empresa);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Estacionamento e WHERE e.id = :id")
    Optional<Estacionamento> findByIdForUpdate(UUID id);
}  
