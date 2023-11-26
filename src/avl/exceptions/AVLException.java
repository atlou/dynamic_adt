package avl.exceptions;

public class AVLException extends Exception {
    public AVLException(String message) {
        super("[AVLException] " + message);
    }
}
