package ru.sstu.socialnetworkbackend.exceptions;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException() {
        super("Время токена истекло");
    }
}
