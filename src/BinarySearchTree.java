public class BinarySearchTree {
    private class Node {
        int key;
        String value;
        Node left;
        Node right;

        public Node(int key, String value) {
            this.key = key;
            this.value = value;
            this.left = new Node();
            this.right = new Node();
        }

        public Node() {
            this.key = -1;
            this.value = null;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public BinarySearchTree() {
        this.root = new Node();
    }

    private boolean isExternal(Node node) {
        return node.key == -1;
    }

    private boolean isRoot(Node node) {
        return node == this.root;
    }

    //--------------- Insertion ---------------//

    public String put(int key, String value) {
        Node n = this.search(key, this.root);

        if(this.isExternal(n)) {
            n.key = key;
            n.value = value;
            n.left = new Node();
            n.right = new Node();
            return null;
        } else {
            // key already in tree
            String old = n.value;
            n.value = value;
            return old;
        }
    }

    //--------------- Parent ---------------//
    // TEST for parentSearch()
    public int getParent(int key) {
        Node p = searchParent(key, this.root);
        if(p != null) {
            return p.key;
        }
        return -2;
    }

    private Node parent(Node node) {
        return searchParent(node.key, this.root);
    }

    /*
    returns the parent node of that key if it exists,
    and returns null if it doesn't
    */
    private Node searchParent(int key, Node curr) {
        if(this.isExternal(curr)) {
            return null;
        }

        if(curr.left.key == key || curr.right.key == key) {
            return curr;
        } else if(key < curr.key) {
            return searchParent(key, curr.left);
        } else {
            return searchParent(key, curr.right);
        }
    }

    //--------------- Search ---------------//



    private Node search(int key, Node curr) {
        if(this.isExternal(curr) || key == curr.key) {
            return curr;
        } else if (key < curr.key) {
            return search(key, curr.left);
        } else {
            return search(key, curr.right);
        }
    }

    public String get(int key) {
        Node n = search(key, this.root);
        if(this.isExternal(n)) {
            return null;
        }
        return n.value;
    }

    //
    // remove()

    //--------------- Next ---------------//
    public int nextKey(int key) {
        Node next = this.searchNext(key, this.root, null);
        if(next != null) return next.key;

        return -2; // TODO: Implement exception instead
    }

    private Node searchNext(int key, Node curr, Node best) {
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
    public int prevKey(int key) {
        Node prev = this.searchPrevious(key, this.root, null);
        if(prev != null) return prev.key;

        return -2; // TODO: Implement exception instead
    }

    private Node searchPrevious(int key, Node curr, Node best) {
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

    // next()
    // prev()
    // listAll()
}
