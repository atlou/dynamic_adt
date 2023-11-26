package avl.exceptions;

public class KeyNotFound extends AVLException {
    public KeyNotFound(int key) {
        super("The key \'" + key + "\' does not exist.");
    }
}
