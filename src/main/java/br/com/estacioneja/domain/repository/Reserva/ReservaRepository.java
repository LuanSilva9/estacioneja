package br.com.estacioneja.domain.repository.Reserva;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.estacioneja.domain.model.Reserva.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {

}
