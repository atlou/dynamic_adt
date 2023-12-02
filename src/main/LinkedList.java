package main;

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

    //------------ SEARCH -------------//
    public String get(long key) {
        Node n = this.search(key);
        return n == null ? null : n.value;
    }

    private Node search(long key) {
        Node curr = this.head;
        while (curr != null) {
            if (curr.key == key) {
                return curr;
            } else if (curr.key > key) {
                System.out.printf("Stopping the search for %d at %d\n", key, curr.key);
                return null;
            } else {
                curr = curr.next;
            }
        }
        return null;
    }


    //------------ INSERTION -------------//
    public String put(long key, String value) {
        Node n = new Node(key, value);
        Node curr = this.head;

        if (this.head == null) {
            this.head = n;
            this.tail = n;
            return null;
        }

        while (curr != null) {
            if (key < curr.key) {
                // insert before curr
                if (curr == this.head) {
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
            } else if (curr == this.tail) {
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
    //------------ REMOVAL -------------//
    public String remove(long key) {
        Node n = this.search(key);
        if (n == null || this.head == null) {
            return null;
        }

        removeNode(n);
        return n.value;
    }

    private void removeNode(Node node) {
        if (node == this.head) {
            this.head = node.next;
        }
        if (node == this.tail) {
            this.tail = node.prev;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
    }

    //------------ PREV NEXT -------------//
    public long prevKey(long key) {
        Node n = this.search(key);
        if (n == null || n.prev == null) {
            return -1; // TODO: add exception
        }

        return n.prev.key;
    }

    public long nextKey(long key) {
        Node n = this.search(key);
        if (n == null || n.next == null) {
            return -1; // TODO: add exception
        }

        return n.next.key;
    }

    //------------ RANGE -------------//
    public int rangeKey(long key1, long key2) {
        // iterate through list starting at key 1
        // stop when key 2 is reached
        int r = 0;
        if (key1 > key2) {
            long temp = key1;
            key1 = key2;
            key2 = temp;
        }
        Node curr = this.head;
        while (curr != null && curr.key <= key2) {
            if (curr.key >= key1) {
//                System.out.printf("key %d is within %d and %d\n", curr.key, key1, key2);
                r++;
            }
            curr = curr.next;
        }

        return r;
    }

    //------------ ALL KEYS -------------//
    public long[] allKeys() {
        int n = this.rangeKey(Long.MIN_VALUE, Long.MAX_VALUE);
        long[] keys = new long[n];

        Node curr = this.head;
        int i = 0;
        while (curr != null) {
            keys[i] = curr.key;
            curr = curr.next;
            i++;
        }

        return keys;
    }

    public String[] allData() {
        int n = this.rangeKey(Long.MIN_VALUE, Long.MAX_VALUE);
        String[] data = new String[n];

        Node curr = this.head;
        int i = 0;
        while (curr != null) {
            data[i] = curr.value;
            curr = curr.next;
            i++;
        }

        return data;
    }
}
