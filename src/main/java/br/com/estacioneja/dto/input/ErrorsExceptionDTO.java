package br.com.estacioneja.dto.input;

public record ErrorsExceptionDTO(String field, String message) {
    public ErrorsExceptionDTO(org.springframework.validation.FieldError erro) {
        this(erro.getField(), erro.getDefaultMessage());
    }
}