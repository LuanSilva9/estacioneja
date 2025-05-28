package br.com.estacioneja.domain.model.Reserva;

public enum ReservaStatus {
    SCHEDULED, IN_PROGRESS, FINISHED
}

/*
 * Breves Explicações -> NEXT_COMMIT
 *  SCHEDULED -> Vaga com o status Agendada, indisponivel para reserva.
 *  IN_PROGRESS -> Vaga com o status Ocupada, veiculo está fisicamente ocupando ela, vaga indisponivel para reserva.
 */