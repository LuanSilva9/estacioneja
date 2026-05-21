package br.com.estacioneja.errors.exceptions;

public class ConectionIsDownException extends RuntimeException {
    public ConectionIsDownException() {
        super("Conexão está inativa");
    }

    public ConectionIsDownException(String message) {
        super(message);
    }
}
