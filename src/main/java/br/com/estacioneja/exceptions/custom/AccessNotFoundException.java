package br.com.estacioneja.exceptions.custom;

public class AccessNotFoundException extends RuntimeException {
    public AccessNotFoundException() {
        super("Acesso não encontrado.");
    }

    public AccessNotFoundException(String message) {
        super(message);
    }
}
