package br.com.estacioneja.exceptions.custom;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {
        super("Usuário com Email ou CPF já cadastrado!");
    }

    public DuplicateUserException(String message) {
        super(message);
    }
}
