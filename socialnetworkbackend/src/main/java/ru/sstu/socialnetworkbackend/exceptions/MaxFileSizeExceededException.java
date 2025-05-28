package ru.sstu.socialnetworkbackend.exceptions;

public class MaxFileSizeExceededException extends RuntimeException {
    public MaxFileSizeExceededException() {
        super("Максимальный размер файла превышен");
    }
}
