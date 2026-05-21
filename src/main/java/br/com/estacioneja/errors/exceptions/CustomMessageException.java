package br.com.estacioneja.errors.exceptions;

public class CustomMessageException extends RuntimeException {
    public CustomMessageException(String message) {
        super(message);
    }
}
