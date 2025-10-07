package br.com.estacioneja.exceptions.custom;

public class ConectionIsDownException extends RuntimeException {
    public ConectionIsDownException() {
        super("Conexão está inativa");
    }
    
    public ConectionIsDownException(String message) {
        super(message);
    }
}
