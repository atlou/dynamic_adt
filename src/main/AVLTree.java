package main;

import main.exceptions.KeyNotFoundException;
import main.exceptions.NoNextKeyException;
import main.exceptions.NoPrevKeyException;
import main.exceptions.avl.NoParentException;
import main.exceptions.avl.NodeIsInternalException;

public class AVLTree {
    private class Node {
        long key;
        String value;
        Node left;
        Node right;
        int height;

        public Node(long key) {
            this.key = key;
            this.value = null;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public AVLTree() {
        this.root = new Node(0);
    }

    private boolean isExternal(Node node) {
        return node.left == null && node.right == null;
    }

    //--------------- AVL TREE ---------------//
    private Node restructure(Node z) {
        /*
        left-heavy
            right (Z>Y>X)
            left-right (Z>X>Y)
        right-heavy
            left (Z<Y<X)
            right-left (Z<X<Y)
         */
        int bal = this.balanceFactor(z);
        if (bal > 1) {
            // left heavy
            Node y = z.left;
            if (this.balanceFactor(y) >= 0) {
                z = rotationRight(z);
            } else {
                z = rotationLeftRight(z);
            }
        }

        if (bal < -1) {
            // right heavy
            Node y = z.right;
            if (this.balanceFactor(y) <= 0) {
                z = rotationLeft(z);
            } else {
                z = rotationRightLeft(z);
            }
        }

        return z;
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    // positive number -> left heavy
    // negative number -> right heavy
    private int balanceFactor(Node node) {
        if (isExternal(node)) {
            return 0;
        } else {
            return getHeight(node.left) - getHeight(node.right);
        }
    }

    private Node rotationLeft(Node z) {
        Node y = z.right;
        z.right = y.left;
        y.left = z;
        updateHeight(z);
        updateHeight(y);
        return y;
    }

    private Node rotationRight(Node z) {
        Node y = z.left;
        z.left = y.right;
        y.right = z;
        updateHeight(z);
        updateHeight(y);
        return y;
    }

    private Node rotationRightLeft(Node z) {
        Node y = z.right;
        z.right = rotationRight(y);
        return rotationLeft(z);
    }

    private Node rotationLeftRight(Node z) {
        Node y = z.left;
        z.left = rotationLeft(y);
        return rotationRight(z);
    }

    private boolean isUnbalanced(Node n) {
        int bal = this.balanceFactor(n);
        return bal > 1 || bal < -1;
    }

    private Node firstUnbalanced(Node curr) {
        Node p = this.getParent(curr);
        if (p == null) {
            return null;
        }

        this.updateHeight(p);
        if (this.isUnbalanced(p)) {
            return p;
        }

        return firstUnbalanced(p);
    }

    //--------------- ALL KEYS ---------------//

    // O(n) time
    // array is O(n) space
    public long[] allKeys() {
        int n = this.rangeKey(Long.MIN_VALUE, Long.MAX_VALUE);
        long[] keys = new long[n];
        inorderKeys(this.root, keys, 0);
//        System.out.printf("[Root] key: %d, height: %d, balance: %d\n", this.root.key, this.root.height, this.balanceFactor(this.root));
//        System.out.printf("[Left] key: %d, height: %d, balance: %d\n", this.root.left.key,  this.root.left.height, this.balanceFactor(this.root.left));
//        System.out.printf("[Right] key: %d, height: %d, balance: %d\n", this.root.right.key, this.root.right.height, this.balanceFactor(this.root.right));
        return keys;
    }

    public String[] allData() {
        int n = this.rangeKey(Long.MIN_VALUE, Long.MAX_VALUE);
        String[] data = new String[n];
        inorderData(this.root, data, 0);
        return data;
    }

    private int inorderKeys(Node node, long[] arr, int i) {
        if (!this.isExternal(node)) {
            i = inorderKeys(node.left, arr, i);
//            System.out.printf("Inserting key %d at i %d\n", node.key, i);
            arr[i] = node.key;
            i++;
            i = inorderKeys(node.right, arr, i);
            return i;
        }
        return i;
    }

    private int inorderData(Node node, String[] arr, int i) {
        if (!this.isExternal(node)) {
            i = inorderData(node.left, arr, i);
//            System.out.printf("Inserting key %d at i %d\n", node.key, i);
            arr[i] = node.value;
            i++;
            i = inorderData(node.right, arr, i);
            return i;
        }
        return i;
    }

    //--------------- KEY RANGE ---------------//

    // O(n) time
    // O(1) space
    public int rangeKey(long key1, long key2) {
        if (key1 < key2) {
            return this.rangeTraversal(key1, key2, this.root);
        } else {
            return this.rangeTraversal(key2, key1, this.root);
        }
    }

    private int rangeTraversal(long min, long max, Node curr) {
        int sum = 0;
        if (!this.isExternal(curr)) {
            if (curr.key >= min && curr.key <= max) {
                sum++;
            }
            if (curr.key >= min) {
                sum += rangeTraversal(min, max, curr.left);
            }
            if (curr.key <= max) {
                sum += rangeTraversal(min, max, curr.right);
            }
        }
        return sum;
    }

    //--------------- Removal ---------------//

    public String remove(long key) throws NodeIsInternalException, NoParentException {
        Node n = this.getNode(key);
        if (n == null) return null;

        String val = n.value;

        if (this.isExternal(n.left)) {
            this.removeExternal(n.left);
        } else if (this.isExternal(n.right)) {
            this.removeExternal(n.right);
        } else {
            // find internal node w that follows n in inorder traversal
            Node w = this.leftmost(n.right);
            // copy w into n
            long k = w.key;
            String v = w.value;
            // remove w and its left child using removeExternal()
            this.removeExternal(w.left);
            n.key = k;
            n.value = v;
        }

        return val;
    }

    // removes node and its parent
    private void removeExternal(Node node) throws NodeIsInternalException, NoParentException {
        if (!this.isExternal(node)) {
            throw new NodeIsInternalException(node.key);
        }

        Node p = this.getParent(node);
        if (p == null) {
            throw new NoParentException(node.key);
        }

        Node gp = this.getParent(p);
        if (gp == null) {
            throw new NoParentException(p.key);
        }

        Node otherChild = p.left == node ? p.right : p.left;

        if (this.isExternal(otherChild)) {
            // if the other child is external, create a new external node with the gp key
            otherChild = new Node(gp.key);
        }

        if (gp.right == p) {
            gp.right = otherChild;
        } else {
            gp.left = otherChild;
        }

        this.updateHeight(gp);

        Node z = this.firstUnbalanced(gp);
        while (z != null) {
            Node b = this.restructure(z);
            if (z == this.root) {
                this.root = b;
            } else {
                Node c = this.getParent(z);
                if (c.right == z) {
                    c.right = b;
                } else {
                    c.left = b;
                }
            }
            z = this.firstUnbalanced(b);
        }
    }

    //--------------- Insertion ---------------//

    public String put(long key, String value) {
        Node n = this.search(key, this.root);

        if (this.isExternal(n)) {
            n.key = key;
            n.value = value;
            n.left = new Node(key);
            n.right = new Node(key);

            this.updateHeight(n);

            Node z = this.firstUnbalanced(n);
            if (z != null) {
                Node b = this.restructure(z);
                if (z == this.root) {
                    this.root = b;
                } else {
                    Node p = this.getParent(z);
                    if (p.right == z) {
                        p.right = b;
                    } else {
                        p.left = b;
                    }
                }
            }
            return null;

        } else {
            // key already in tree, just replace value
            String old = n.value;
            n.value = value;
            return old;
        }
    }

    //--------------- Parent ---------------//

    private Node getParent(Node child) {
        return searchParent(child, this.root);
    }

    private Node searchParent(Node child, Node curr) {
        if (curr.left == child || curr.right == child) {
            return curr;
        } else if (!this.isExternal(curr.left) && child.key < curr.key) {
            return searchParent(child, curr.left);
        } else if (!this.isExternal(curr.right) && child.key > curr.key) {
            return searchParent(child, curr.right);
        }

        return null;
    }

    //--------------- Search ---------------//

    private Node search(long key, Node curr) {
        if (this.isExternal(curr) || key == curr.key) {
            return curr;
        } else if (key < curr.key) {
            return search(key, curr.left);
        } else {
            return search(key, curr.right);
        }
    }

    public String getValue(long key) {
        Node n = search(key, this.root);
        if (this.isExternal(n)) {
            return null;
        }
        return n.value;
    }

    private Node getNode(long key) {
        return search(key, this.root);
    }

    //--------------- Next ---------------//

    public long nextKey(long key) throws KeyNotFoundException, NoNextKeyException {
        Node node = this.search(key, this.root);
        if (node == null || this.isExternal(node)) {
            throw new KeyNotFoundException(key);
        }
        Node next = this.searchNext(key, this.root, null);
        if (next == null) {
            throw new NoNextKeyException(key);
        }
        return next.key;
    }

    private Node searchNext(long key, Node curr, Node best) {
        if (this.isExternal(curr)) {
            return best;
        }
        if (key < curr.key) {
            if (key + 1 == curr.key) {
                return curr;
            }
            if (best == null || curr.key < best.key) {
                best = curr;
            }
            return searchNext(key, curr.left, best);
        } else {
            return searchNext(key, curr.right, best);
        }
    }

    //--------------- Prev ---------------//

    public long prevKey(long key) throws KeyNotFoundException, NoPrevKeyException {
        Node node = this.search(key, this.root);
        if (node == null || this.isExternal(node)) {
            throw new KeyNotFoundException(key);
        }
        Node prev = this.searchPrevious(key, this.root, null);
        if (prev == null) {
            throw new NoPrevKeyException(key);
        }

        return prev.key;
    }

    private Node searchPrevious(long key, Node curr, Node best) {
        if (this.isExternal(curr)) {
            return best;
        }
        if (key > curr.key) {
            if (key - 1 == curr.key) {
                return curr;
            }
            if (best == null || curr.key > best.key) {
                best = curr;
            }
            return searchPrevious(key, curr.right, best);
        } else {
            return searchPrevious(key, curr.left, best);
        }
    }

    //--------------- Utility ---------------//

    private Node leftmost(Node curr) {
        if (!isExternal(curr.left)) {
            return leftmost(curr.left);
        } else {
            return curr;
        }
    }

    private Node rightmost(Node curr) {
        if (!isExternal(curr.right)) {
            return rightmost(curr);
        } else {
            return curr;
        }
    }
}
