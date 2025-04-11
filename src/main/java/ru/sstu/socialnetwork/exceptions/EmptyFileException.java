package ru.sstu.socialnetwork.exceptions;

public class EmptyRequestException extends RuntimeException {

    public EmptyRequestException() {
        super("Пароли не совпадают");
    }

    public EmptyRequestException(String message) {
        super(message);
    }

    public EmptyRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRequestException(Throwable cause) {
        super(cause);
    }

}
