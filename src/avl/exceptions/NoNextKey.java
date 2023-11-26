package avl.exceptions;

public class NoNextKey extends AVLException {
    public NoNextKey(int key) {
        super("The key \'" + key + "\' has no successor.");
    }
}