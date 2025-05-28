package ru.sstu.socialnetworkbackend.exceptions;

public class IncorrectSearchTermException extends RuntimeException {
    public IncorrectSearchTermException() {
        super("Некорректное условие поиска");
    }
}
