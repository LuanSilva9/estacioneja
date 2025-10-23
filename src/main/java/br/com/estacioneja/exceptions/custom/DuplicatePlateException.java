package br.com.estacioneja.exceptions.custom;

public class DuplicatePlateException extends RuntimeException {
    public DuplicatePlateException() {
        super("Já existe um veiculo cadastrado com essa placa");
    }

    public DuplicatePlateException(String message) {
        super(message);
    }
}
