package main.exceptions;

public class NoNextKeyException extends Exception {
    public NoNextKeyException(long key) {
        super(String.format("NoNextKeyException: There is no key after %d.", key));
    }
}
