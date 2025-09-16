package br.com.estacioneja.exceptions.custom;

public class ParkIsPublicException extends RuntimeException {
    public ParkIsPublicException() {
        super("Esse estacionamento é público, não há necessidade de solicitar um vinculo");
    }

    public ParkIsPublicException(String message) {
        super(message);
    }
}

