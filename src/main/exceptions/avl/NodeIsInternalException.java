package main.exceptions.avl;

public class NodeIsInternalException extends AVLTreeException {
    public NodeIsInternalException(long key) {
        super(String.format("NodeIsInternalException: The node with key %d is not external.", key));
    }
}
