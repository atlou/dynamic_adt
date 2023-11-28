public class BinarySearchTree {
    private class Node {
        long key;
        String value;
        Node left;
        Node right;

        public Node(long key) {
            this.key = key;
            this.value = null;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public BinarySearchTree() {
        this.root = new Node(0);
    }

    private boolean isExternal(Node node) {
        return node.left == null && node.right == null;
    }

    private boolean isRoot(Node node) {
        return node == this.root;
    }

    //--------------- ALL KEYS ---------------//
    // O(n) time
    // array is O(n) space
    public long[] allKeys() {
        System.out.println("ALL KEYS");
        int n = this.rangeKey(Long.MIN_VALUE, Long.MAX_VALUE);
        long[] keys = new long[n];
        inorder(this.root, keys, 0);
        return keys;
    }

    private int inorder(Node node, long[] arr, int i) {
        if(!this.isExternal(node)) {
            i = inorder(node.left, arr, i);
//            System.out.printf("Inserting key %d at i %d\n", node.key, i);
            arr[i] = node.key;
            i++;
            i = inorder(node.right, arr, i);
            return i;
        }
        return i;
    }

    //--------------- KEY RANGE ---------------//
    // O(n) time
    // O(1) space
    public int rangeKey(long key1, long key2) {
        if(key1 < key2) {
            return this.rangeTraversal(key1, key2, this.root);
        } else {
            return this.rangeTraversal(key2, key1, this.root);
        }
    }

    private int rangeTraversal(long min, long max, Node curr) {
        int sum = 0;
        if(!this.isExternal(curr)) {
            if(curr.key > min && curr.key < max) {
                sum++;
            }
            if(curr.key > min) {
                sum += rangeTraversal(min, max, curr.left);
            }
            if(curr.key < max) {
                sum += rangeTraversal(min, max, curr.right);
            }
        }
        return sum;
    }

    //--------------- Removal ---------------//
    public String remove(long key) {
        System.out.println("Removing key " + key);

        Node n = this.getNode(key);
        if(n == null) return null;

        String val = n.value;

        if(this.isExternal(n.left)) {
            this.removeExternal(n.left);
        } else if(this.isExternal(n.right)) {
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
    // TODO: Add exceptions instead of prints
    private void removeExternal(Node node) {
        if(!this.isExternal(node)) {
            System.out.printf("removeExternal fail: %d is not external.\n", node.key);
            return;
        }

        Node p = this.getParent(node);

        if(p == null) {
            System.out.printf("Could not find parent of %d.\n", node.key);
            return;
        }
        Node gp = this.getParent(p);
        if(gp == null) {
            System.out.printf("Could not find parent of %d.\n", p.key);
            return;
        }

        Node otherChild = p.left == node ? p.right : p.left;

        if(gp.right == p) {
            gp.right = otherChild;
        } else {
            gp.left = otherChild;
        }

    }

    //--------------- Insertion ---------------//
    public String put(long key, String value) {
        Node n = this.search(key, this.root);

        if(this.isExternal(n)) {
            n.key = key;
            n.value = value;
            n.left = new Node(key);
            n.right = new Node(key);
            return null;
        } else {
            // key already in tree
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
        if(curr.left == child || curr.right == child) {
            return curr;
        } else if(!this.isExternal(curr.left) && child.key < curr.key) {
            return searchParent(child, curr.left);
        } else if(!this.isExternal(curr.right) && child.key > curr.key) {
            return searchParent(child, curr.right);
        }

        return null;
    }

    //--------------- Search ---------------//
    private Node search(long key, Node curr) {
        if(this.isExternal(curr) || key == curr.key) {
            return curr;
        } else if (key < curr.key) {
            return search(key, curr.left);
        } else {
            return search(key, curr.right);
        }
    }

    public String getValue(long key) {
        Node n = search(key, this.root);
        if(this.isExternal(n)) {
            return null;
        }
        return n.value;
    }

    private Node getNode(long key) {
        return search(key, this.root);
    }

    //--------------- Next ---------------//
    public long nextKey(long key) {
        Node next = this.searchNext(key, this.root, null);
        if(next != null) return next.key;

        return -2; // TODO: Implement exception instead
    }

    private Node searchNext(long key, Node curr, Node best) {
        if(this.isExternal(curr)) {
            return best;
        }
        if(key < curr.key) {
            if(key + 1 == curr.key) {
                return curr;
            }
            if(best == null || curr.key < best.key) {
                best = curr;
            }
            return searchNext(key, curr.left, best);
        } else {
            return searchNext(key, curr.right, best);
        }
    }

    //--------------- Prev ---------------//
    public long prevKey(long key) {
        Node prev = this.searchPrevious(key, this.root, null);
        if(prev != null) return prev.key;

        return -2; // TODO: Implement exception instead
    }

    private Node searchPrevious(long key, Node curr, Node best) {
        if(this.isExternal(curr)) {
            return best;
        }
        if(key > curr.key) {
            if(key - 1 == curr.key) {
                return curr;
            }
            if(best == null || curr.key > best.key) {
                best = curr;
            }
            return searchPrevious(key, curr.right, best);
        } else {
            return searchPrevious(key, curr.left, best);
        }
    }

    //--------------- Utility ---------------//
    private Node leftmost(Node curr) {
        if(!isExternal(curr.left)) {
            return leftmost(curr);
        } else {
            return curr;
        }
    }

    private Node rightmost(Node curr) {
        if(!isExternal(curr.right)) {
            return rightmost(curr);
        } else {
            return curr;
        }
    }
}
