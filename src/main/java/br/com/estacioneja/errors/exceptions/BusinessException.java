package br.com.estacioneja.errors.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException() {
        super("Erro na regra de negocio");
    }
}
