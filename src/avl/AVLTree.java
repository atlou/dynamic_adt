package avl;

import avl.exceptions.NoNextKey;
import avl.exceptions.KeyNotFound;

public class AVLTree {
    private class Node {
        int key;
        String value;
        Node left;
        Node right;

        public Node() {
            this.key = 0;
            this.value = null;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public AVLTree() {
        this.root = new Node();
    }

    private boolean isRoot(Node node) {
        return this.root == node;
    }

    private boolean isExternal(Node node) {
        return node.left == null && node.right == null;
    }

    private Node search(int key, Node node) {
        if (this.isExternal(node)) {
            return null;
        }
        if (key < node.key) {
            return search(key, node.left);
        }
        else if (key == node.key) {
            return node;
        }
        else {
            return search(key, node.right);
        }
    }

    // returns the parent of node
    // returns null if node is root
    private Node parent(Node node) {
        return parentSearch(node.key, this.root);
    }

    private Node parentSearch(int key, Node node) {
        if (this.isExternal(node) || key == node.key) {
            // parent was not found or node is root
            return null;
        }

        if (key == node.left.key || key == node.right.key) {
            return node;
        }
        else if (key < node.key) {
            return parentSearch(key, node.left);
        }
        else {
            return parentSearch(key, node.right);
        }
    }

    private Node insertionSearch(int key, Node node) {
        if (this.isExternal(node) || key == node.key) {
            return node;
        }
        if (key < node.key) {
            return insertionSearch(key, node.left);
        }
        else {
            return insertionSearch(key, node.right);
        }
    }

    public String get(int key) {
        Node n = search(key, root);
        return n == null ? null : n.value;
    }

    public String put(int key, String value) {
        Node node = this.insertionSearch(key, this.root);

        if (node.value == null) {
            // new key
            node.key = key;
            node.value = value;
            node.left = new Node();
            node.right = new Node();
            return null;
        }
        else {
            // existing key
            String oldValue = node.value;
            node.value = value;
            return oldValue;
        }

    }

//    public String remove(int key) {
//
//    }

    // TODO: must return a collection of values
    public void allKeys() {
        traversal(this.root);
    }

    private void traversal(Node node)
    {
        if (!this.isExternal(node)) {
            traversal(node.left);
            System.out.println(node.key);
            traversal(node.right);
        }
    }

    // returns true if node is a left child
    // returns false if node is a right child
    private boolean isLeftChild(Node node) {
        return this.parent(node).key > node.key;
    }

    public int nextKey(int key) throws NoNextKey, KeyNotFound {
        Node n = search(key, this.root);
        if(n == null) throw new KeyNotFound(key);

        Node next = this.next(n);
        if(next == null) throw new NoNextKey(key);

        return next.key;
    }

    private Node next(Node node) {
        if (this.isExternal(node)) return null;
        if(node.right.value != null) return node.right;

        Node p = this.parent(node);
        if(node.key < p.key) {
            // n is a left child
            // so the next key is its parent
            return p;
        } else {
            // n is a right child
            // move up the tree until we find a left child and return its parent
            return leftChildSearch(node);
        }
    }

    // Starts from node and moves up the tree until it finds a left child
    // Returns the parent of that left child
    private Node leftChildSearch(Node node) {
        if(this.isRoot(node)) {
            return null;
        }
        if(this.isLeftChild(node)) return this.parent(node);

        return leftChildSearch(this.parent(node));
    }

    // Starts from node and moves up the tree until it finds a right child
    // Returns the parent of that right child
    private Node rightChildSearch(Node node) {
        if(this.isRoot(node)) {
            return null;
        }
        if(!this.isLeftChild(node)) {
            return this.parent(node);
        }
        return rightChildSearch(this.parent(node));
    }
}
