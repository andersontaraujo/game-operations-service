package br.com.keepmygaming.gameoperationsservice.exceptions;

public class RatingNotFoundException extends RuntimeException {
    public RatingNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
}