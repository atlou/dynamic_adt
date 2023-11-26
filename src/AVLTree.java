public class AVLTree {
    private class Node {
        int key;
        String value;
        Node left;
        Node right;

        public Node() {
            this.key = 0;
            this.value = "";
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public AVLTree() {
        this.root = null;
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

    private Node insertionSearch(int key, Node node) {
        if (this.isExternal(node)) {
            return node;
        }
        if (key < node.key) {
            return search(key, node.left);
        }
        else if (key == node.key) {
            return null;
        }
        else {
            return search(key, node.right);
        }
    }

    public Node get(int key) {
        return search(key, root);
    }

    public void put(int key, String value) {
        Node node = this.insertionSearch(key, this.root);
        if (node == null) {
            System.out.println("The key is already in the tree.");
            return;
        }

        node.key = key;
        node.value = value;
        node.left = new Node();
        node.right = new Node();
    }
}
