package br.com.estacioneja.exceptions.custom;

public class VacancyIsNotAvailableException extends RuntimeException {
    public VacancyIsNotAvailableException() {
        super("Vaga está ocupada / agendada.");
    }

    public VacancyIsNotAvailableException(String message) {
        super(message);
    }
}
