package br.com.estacioneja.exceptions.custom;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuario não encontrado.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
