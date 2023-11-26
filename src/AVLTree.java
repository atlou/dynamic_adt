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
}
