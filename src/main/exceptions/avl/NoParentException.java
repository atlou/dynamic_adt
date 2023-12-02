package main.exceptions.avl;

public class NoParentException extends AVLTreeException {
    public NoParentException(long key) {
        super(String.format("NoParentException: The node with key %d has no parent.", key));
    }
}
