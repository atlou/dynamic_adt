package main.exceptions;

public class TooManyAttemptsException extends Exception {
    public TooManyAttemptsException(String message) {
        super("TooManyAttemptsException: " + message);
    }
}
