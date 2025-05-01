package ru.sstu.socialnetworkbackend.exceptions;

public class EmptyFileException extends RuntimeException {
    public EmptyFileException() {
        super("Файл не найден");
    }
}
