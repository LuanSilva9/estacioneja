package br.com.estacioneja.exceptions.custom;

public class ConectionIsNotSupportedException extends RuntimeException {
    public ConectionIsNotSupportedException() {
        super("Conexão não suportada, Estamos trabalhando para suportar mais tipos de conexão");
    }
    
    public ConectionIsNotSupportedException(String message) {
        super(message);
    }
    
}
