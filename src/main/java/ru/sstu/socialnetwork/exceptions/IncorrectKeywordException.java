package ru.sstu.socialnetwork.exceptions;

public class IncorrectKeywordException extends RuntimeException {

    public IncorrectKeywordException() {
        super("Некорректное ключевое слово");
    }

}
