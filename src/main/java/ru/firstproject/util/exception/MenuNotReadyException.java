package ru.firstproject.util.exception;

public class MenuNotReadyException extends RuntimeException {
    public MenuNotReadyException(String message) {
        super(message);
    }
}
