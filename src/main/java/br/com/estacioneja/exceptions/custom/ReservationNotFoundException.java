package br.com.estacioneja.exceptions.custom;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException() {
        super("Reserva não encontrada.");
    }

    public ReservationNotFoundException(String message) {
        super(message);
    }
}
