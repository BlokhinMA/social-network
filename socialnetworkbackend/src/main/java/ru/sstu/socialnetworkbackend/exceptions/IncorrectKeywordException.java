package ru.sstu.socialnetworkbackend.exceptions;

public class IncorrectKeywordException extends RuntimeException {
    public IncorrectKeywordException() {
        super("Некорректное ключевое слово");
    }
    public IncorrectKeywordException(String message) {
        super(message);
    }
}
