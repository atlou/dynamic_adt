public class BinarySearchTree {
    private class Node {
        int key;
        String value;
        Node left;
        Node right;

        public Node(int key, String value) {
            this.key = key;
            this.value = value;
            this.left = new Node(key);
            this.right = new Node(key);
        }

        public Node(int key) {
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

    //--------------- ALLKEYS ---------------//
    // TEST: allKeys
    public void allKeys() {
        System.out.println("ALL KEYS");
        traversal(this.root);
    }


    private void traversal(Node node) {
        if(!this.isExternal(node)) {
            traversal(node.left);
            System.out.println(node.key);
            traversal(node.right);
        }
    }

    //--------------- Removal ---------------//
    public String remove(int key) {
        System.out.println("Removing key " + key);

        Node n = getNode(key);
        if(n == null) return null;

        String val = n.value;

        if(isExternal(n.left)) {
            removeExternal(n.left);
        } else if(isExternal(n.right)) {
            removeExternal(n.right);
        } else {
            // find internal node w that follows n in inorder traversal
            Node w = leftmost(n.right);
            // copy w into n
            int k = w.key;
            String v = w.value;
            // remove w and its left child using removeExternal()
            removeExternal(w.left);
            n.key = k;
            n.value = v;
        }

        return val;
    }

//    private void removeRecursive(Node node) {
//        if(isExternal(node.left)) {
//            removeExternal(node.left);
//        } else if (isExternal(node.right)) {
//            removeExternal(node.right);
//        } else {
//
//        }
//    }

    // removes node and its parent
    private void removeExternal(Node node) {
        if(!this.isExternal(node)) {
            System.out.printf("removeExternal fail: %d is not external.\n", node.key);
            return;
        }

        // delete parent of external
            // make grandparent link to the other child of parent
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

    public String put(int key, String value) {
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
    // TEST for parentSearch()
    public int getParentTest(int key) {
        Node c = getNode(key);
        Node p = searchParent(c, this.root);
        if(p != null) {
            return p.key;
        }
        return -2;
    }

    private Node getParent(Node child) {
        return searchParent(child, this.root);
    }

    /*
    returns the parent if it exists,
    returns null otherwise
    */
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
    private Node search(int key, Node curr) {
        if(this.isExternal(curr) || key == curr.key) {
            return curr;
        } else if (key < curr.key) {
            return search(key, curr.left);
        } else {
            return search(key, curr.right);
        }
    }

    public String getValue(int key) {
        Node n = search(key, this.root);
        if(this.isExternal(n)) {
            return null;
        }
        return n.value;
    }

    private Node getNode(int key) {
        return search(key, this.root);
    }

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
