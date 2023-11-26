package avl.exceptions;

public class NoPreviousKey extends AVLException {
    public NoPreviousKey(int key) {
        super("The key \'" + key + "\' has no predecessor.");
    }
}
