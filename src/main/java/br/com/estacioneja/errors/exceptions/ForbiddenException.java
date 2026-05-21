package br.com.estacioneja.errors.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("Você não tem permissão para acessar!");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
