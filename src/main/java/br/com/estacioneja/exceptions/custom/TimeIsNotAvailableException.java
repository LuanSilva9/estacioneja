package br.com.estacioneja.exceptions.custom;

public class TimeIsNotAvailableException  extends RuntimeException {
    public TimeIsNotAvailableException() {
        super("Você já fez uma reserva nesse horario");
    }

    public TimeIsNotAvailableException(String message) {
        super(message);
    }
}
