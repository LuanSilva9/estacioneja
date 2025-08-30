package br.com.estacioneja.exceptions.custom;

public class DuplicateCompanyException extends RuntimeException {
    public DuplicateCompanyException() {
        super("CPNJ já existente");
    }

    public DuplicateCompanyException(String message) {
        super(message);
    }
}
