package br.com.estacioneja.errors.exceptions;

public class DuplicateCompanyException extends RuntimeException {
    public DuplicateCompanyException() {
        super("CPNJ já existente");
    }

    public DuplicateCompanyException(String message) {
        super(message);
    }
}
