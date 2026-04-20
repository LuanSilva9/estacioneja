package br.com.estacioneja.exceptions.custom;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    } 

    public BusinessException() {
        super("Erro na regra de negocio");
    }
}
