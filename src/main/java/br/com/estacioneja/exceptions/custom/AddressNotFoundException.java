package br.com.estacioneja.exceptions.custom;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException() {
        super("Endereco não encontrado");
    }

    public AddressNotFoundException(String message) {
        super(message);
    }
}
