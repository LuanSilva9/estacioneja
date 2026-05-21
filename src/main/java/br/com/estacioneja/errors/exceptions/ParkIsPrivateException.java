package br.com.estacioneja.errors.exceptions;

public class ParkIsPrivateException extends RuntimeException {
    public ParkIsPrivateException() {
        super("Esse estacionamento é privado, solicite o vinculo antes de entrar");
    }

    public ParkIsPrivateException(String message) {
        super(message);
    }
}
