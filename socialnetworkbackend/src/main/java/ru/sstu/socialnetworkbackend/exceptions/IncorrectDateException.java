package ru.sstu.socialnetworkbackend.exceptions;

public class IncorrectDateException extends RuntimeException {
    public IncorrectDateException(String message) {
        super(message);
    }
}
