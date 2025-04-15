package ru.sstu.socialnetwork.exceptions;

public class IncorrectSearchTermException extends RuntimeException {

    public IncorrectSearchTermException() {
        super("Некорректное условие поиска");
    }

}
