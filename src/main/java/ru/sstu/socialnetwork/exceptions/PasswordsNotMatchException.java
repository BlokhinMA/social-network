package ru.sstu.socialnetwork.exceptions;

public class PasswordsNotMatchException extends RuntimeException {

    public PasswordsNotMatchException() {
        super("Пароли не совпадают");
    }

}
