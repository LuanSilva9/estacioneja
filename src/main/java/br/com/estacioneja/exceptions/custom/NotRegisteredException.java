package br.com.estacioneja.exceptions.custom;

public class NotRegisteredException extends RuntimeException {
    public NotRegisteredException() {
        super("Não foi possivel cadastrar o Representante.");
    }

    public NotRegisteredException(String message) {
        super(message);
    }
    
}
