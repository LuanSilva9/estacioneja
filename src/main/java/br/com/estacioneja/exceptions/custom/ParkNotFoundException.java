package br.com.estacioneja.exceptions.custom;

public class ParkNotFoundException extends RuntimeException {
    public ParkNotFoundException() {
        super("Estacionamento não encontrado.");
    }

    public ParkNotFoundException(String message) {
        super(message);
    }
}
