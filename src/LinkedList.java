public class LinkedList {
    private class Node {
        long key;
        String value;
        Node prev;
        Node next;

        public Node(long key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node head;
    private Node tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    // search
    public String get(long key) {
        Node n = this.search(key);
        return n == null ? null : n.value;
    }

    private Node search(long key) {
        Node curr = this.head;
        while(curr != null) {
            if(curr.key == key) {
                return curr;
            } else if(curr.key > key) {
                System.out.printf("Stopping the search for %d at %d\n", key, curr.key);
                return null;
            } else {
                curr = curr.next;
            }
        }
        return null;
    }

    // insert new key
    // replace old value if key already exists, and return old value
    public String put(long key, String value) {
        // search if key already exists
        Node old = this.search(key);
        if(old == null) {
            // insert new key
            this.insertAtHead(key, value);
        } else {
            // replace existing value
            String oldValue = old.value;
            old.value = value;
            return oldValue;
        }
        return null;
    }

    // inserts new node at head
    private void insertAtHead(long key, String value) {
        Node n = new Node(key, value);
        if(this.head == null) {
            this.head = n;
            this.tail = n;
        } else {
            n.next = this.head;
            this.head.prev = n;
            this.head = n;
        }
    }

    // TODO: This will become put()
    public String insertSorted(long key, String value) {
        Node n = new Node(key, value);
        Node curr = this.head;

        if(this.head == null) {
            this.head = n;
            this.tail = n;
            return null;
        }

        while(curr != null) {
            if (key < curr.key) {
                // insert before curr
                if(curr == this.head) {
                    n.next = curr;
                    curr.prev = n;
                    this.head = n;
                } else {
                    n.prev = curr.prev;
                    n.prev.next = n;
                    n.next = curr;
                    curr.prev = n;
                }
                return null;
            } else if (key == curr.key) {
                // replace value of curr
                String oldVal = curr.value;
                curr.value = value;
                return oldVal;
            } else if(curr == this.tail) {
                curr.next = n;
                n.prev = curr;
                this.tail = n;
                return null;
            } else {
                curr = curr.next;
            }
        }

        return null;
    }

    // remove
    public String remove(long key) {
        Node n = this.search(key);
        if(n == null || this.head == null) {
            return null;
        }

        removeNode(n);
        return n.value;
    }

    private void removeNode(Node node) {

        if(node == this.head) {
            this.head = node.next;
        }

        if(node == this.tail) {
            this.tail = node.prev;
        }

        if(node.next != null) {
            node.next.prev = node.prev;
        }

        if(node.prev != null) {
            node.prev.next = node.next;
        }
    }

    // merge sort


    // prev next
//    public long prevKey(long key) {
//        Node n = this.search(key);
//        if(n == null) {
//            return -1; // TODO: add exception
//        }
//
//        return
//    }

    // getAll
    public String allKeys() {
        String keys = "";
        Node curr = this.head;
        while(curr != null) {
            keys += curr.key + ", ";
            curr = curr.next;
        }
        return keys;
    }
}
