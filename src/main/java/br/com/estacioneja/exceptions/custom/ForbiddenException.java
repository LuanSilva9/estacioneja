package br.com.estacioneja.exceptions.custom;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("Você não tem permissão para acessar!");
    }

    public ForbiddenException(String message) {
        super(message);
    }

    
}
