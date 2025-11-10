package br.com.estacioneja.exceptions.custom;

public class ParkSizeViolatedException extends RuntimeException {
    public ParkSizeViolatedException() {
        super("Capacidade disponivel do estacionamento foi violada");
    }

    public ParkSizeViolatedException(String message) {
        super(message);
    }
}
