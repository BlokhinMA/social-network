package ru.sstu.socialnetwork.exceptions;

public class EmptyFileException extends RuntimeException {

    public EmptyFileException() {
        super("Файл не найден");
    }

}
