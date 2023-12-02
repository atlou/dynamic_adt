package main.exceptions;

public class KeyNotFoundException extends Exception {
    public KeyNotFoundException(long key) {
        super(String.format("KeyNotFoundException: Could not find the key %d.", key));
    }
}
