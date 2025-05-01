package ru.sstu.socialnetworkbackend.exceptions;

public class PasswordsNotMatchException extends RuntimeException {
    public PasswordsNotMatchException() {
        super("Пароли не совпадают");
    }
}
