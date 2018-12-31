package ru.firstproject.util.exception;

public class VoteAlreadyStarted extends RuntimeException {
    public VoteAlreadyStarted(String message) {
        super(message);
    }
}
