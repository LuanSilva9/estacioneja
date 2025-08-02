package br.com.estacioneja.exceptions.custom;

public class VacancyNotFoundException extends RuntimeException {
    public VacancyNotFoundException() {
        super("Vaga não encontrada.");
    }

    public VacancyNotFoundException(String message) {
        super(message);
    }
}
