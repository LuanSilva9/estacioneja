package br.com.estacioneja.exceptions.custom;

public class CustomMessageException extends RuntimeException {
    public CustomMessageException(String message) {
        super(message);
    }
}
