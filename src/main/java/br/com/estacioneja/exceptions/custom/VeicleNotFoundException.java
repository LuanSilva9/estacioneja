package br.com.estacioneja.exceptions.custom;

public class VeicleNotFoundException extends RuntimeException {
    public VeicleNotFoundException() {
        super("Veiculo não encontrado.");
    }

    public VeicleNotFoundException(String message) {
        super(message);
    }
}
