package ru.sstu.socialnetwork.exceptions;

public class PasswordsNotMatchException extends RuntimeException {

    public PasswordsNotMatchException() {
        super("Пароли не совпадают");
    }

    public PasswordsNotMatchException(String message) {
        super(message);
    }

    public PasswordsNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordsNotMatchException(Throwable cause) {
        super(cause);
    }

}
