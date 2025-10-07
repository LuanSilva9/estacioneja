package br.com.estacioneja.exceptions.custom;

public class FilialNotFoundException extends RuntimeException {
    public FilialNotFoundException() {
        super("Filial não encontrada");
    }

    public FilialNotFoundException(String message) {
        super(message);
    }
    
}
