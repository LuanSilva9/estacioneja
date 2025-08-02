package br.com.estacioneja.exceptions.custom;

public class ParkIsFullException extends RuntimeException {
    public ParkIsFullException() {
        super("Estacionamento está lotado no momento.");
    }

    public ParkIsFullException(String message) {
        super(message);
    }
}
