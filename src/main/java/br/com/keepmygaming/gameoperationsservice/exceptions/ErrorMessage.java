package br.com.keepmygaming.gameoperationsservice.exceptions;

public final class ErrorMessage {

    private ErrorMessage() {}

    public static final String RATING_NOT_FOUND_MESSAGE = "Could not find any rating with id [%s].";
    public static final String GAME_RATING_NOT_FOUND_MESSAGE = "Could not find any rating associated with gameId [%s].";
}