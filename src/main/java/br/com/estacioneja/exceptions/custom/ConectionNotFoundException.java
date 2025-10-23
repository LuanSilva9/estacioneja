package br.com.estacioneja.exceptions.custom;

public class ConectionNotFoundException extends RuntimeException {
    public ConectionNotFoundException() {
        super("Conexão não encontrada");
    }
    
    public ConectionNotFoundException(String message) {
        super(message);
    }
    
}
