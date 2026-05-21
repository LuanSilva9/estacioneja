package br.com.estacioneja.errors.exceptions;

public class DuplicatePlateException extends RuntimeException {
    public DuplicatePlateException() {
        super("Já existe um veiculo cadastrado com essa placa");
    }

    public DuplicatePlateException(String message) {
        super(message);
    }
}
