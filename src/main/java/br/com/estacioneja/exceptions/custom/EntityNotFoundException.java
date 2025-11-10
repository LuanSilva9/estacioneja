package br.com.estacioneja.exceptions.custom;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Entidade não encontrada");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
