package br.com.estacioneja.errors;

public record ValidationFieldError(String field, String message) {
    public ValidationFieldError(org.springframework.validation.FieldError erro) {
        this(erro.getField(), erro.getDefaultMessage());
    }
}
