package ru.sstu.socialnetworkbackend.exceptions;

public class TokenAlreadyConfirmed extends RuntimeException {
    public TokenAlreadyConfirmed() {
        super("Аккаунт уже подтвержден");
    }
}
