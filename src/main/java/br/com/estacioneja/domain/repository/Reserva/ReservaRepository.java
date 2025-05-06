package br.com.estacioneja.domain.repository.Reserva;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Reserva.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, UUID> {
    @Query("SELECT r FROM Reserva r WHERE r.vaga.estacionamento.id = :estacionamentoId")
    List<Reserva> findByEstacionamentoId(@Param("estacionamentoId") UUID estacionamentoId);

    @Query("SELECT COUNT(v) FROM Vaga v LEFT JOIN Reserva r ON v.id = r.vaga.id WHERE v.id = :vagaId AND (r.status = 2 OR r.status IS NULL)")
    Long countByAvaliable(@Param("vagaId") UUID vagaId);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.usuario.id = :usuarioId AND r.horarioEntrada < :horarioSaida AND r.horarioSaida > :horarioEntrada")
    Long countByTimeConflicts(@Param("usuarioId") Long usuarioId, @Param("horarioEntrada") ZonedDateTime horarioEntrada, @Param("horarioSaida") ZonedDateTime horarioSaida);

}
