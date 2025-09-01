package br.com.estacioneja.exceptions.custom;

public class SolicitationNotFoundException extends RuntimeException {
    public SolicitationNotFoundException() {
        super("Solicitação não encontrada");
    }

    public SolicitationNotFoundException(String message) {
        super(message);
    }
}
