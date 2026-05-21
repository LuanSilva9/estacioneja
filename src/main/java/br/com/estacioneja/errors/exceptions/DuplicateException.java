package br.com.estacioneja.errors.exceptions;

public class DuplicateException extends RuntimeException {
    public DuplicateException() {
        super("Usuário com Email ou CPF já cadastrado!");
    }

    public DuplicateException(String message) {
        super(message);
    }
}
