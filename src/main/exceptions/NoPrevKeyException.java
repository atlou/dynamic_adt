package main.exceptions;

public class NoPrevKeyException extends Exception {
    public NoPrevKeyException(long key) {
        super(String.format("NoPrevKeyException: There is no key before %d.", key));
    }
}