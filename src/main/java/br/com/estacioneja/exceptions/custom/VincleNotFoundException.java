package br.com.estacioneja.exceptions.custom;

public class VincleNotFoundException extends RuntimeException {
    public VincleNotFoundException() {
        super("Vinculo não encontrado.");
    }

    public VincleNotFoundException(String message) {
        super(message);
    }
}
