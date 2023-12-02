package main;

import java.util.concurrent.ThreadLocalRandom;

public class CleverSIDC {

    private boolean largeData;
    private int threshold;
    private int count;
    private LinkedList list;
    private AVLTree avl;

    public CleverSIDC() {
        this.largeData = false;
        this.threshold = 10000;
        this.count = 0;
        this.list = new LinkedList();
        this.avl = new AVLTree();
    }

    private void incrementCount() {
        this.count++;
        updateMode();
    }

    private void decrementCount() {
        this.count--;
        updateMode();
    }

    private void updateMode() {
        // small data goes over threshold
        if(this.count >= this.threshold && !this.largeData) {
            this.largeData = true;
            this.listToAVL();
        }
        // large data goes under threshold
        if(this.count < this.threshold && this.largeData) {
            this.largeData = false;
            this.avlToList();
        }
    }

    private void listToAVL() {
        System.out.println("Switching from Linked List to AVL Tree");
        long[] keys = this.list.allKeys();
        String[] data = this.list.allData();

        for(int i = 0; i < keys.length; i++) {
            this.avl.put(keys[i], data[i]);
        }

        this.list = new LinkedList();
    }

    private void avlToList() {
        System.out.println("Switching from AVL Tree to Linked List");
        long[] keys = this.avl.allKeys();
        String[] data = this.avl.allData();

        for(int i = 0; i < keys.length; i++) {
            this.list.put(keys[i], data[i]);
        }

        this.avl = new AVLTree();
    }

    // sets the threshold at which the data structure switches from a linked list to an avl tree,
    // and vice-versa
    public void setSIDCThreshold(int threshold) {
        this.threshold = threshold;
    }

    // randomly generates new non-existing key of 8 digits
    public long generate() {
        int maxAttempts = 10000;
        long min = 1;
        long max = 10;
//        long min = 10000000;
//        long max = 100000000;
        long key;
        String v;
        int i = 0;
        do {
            key = ThreadLocalRandom.current().nextLong(min, max);
            v = this.largeData ? this.avl.getValue(key) : this.list.get(key);
            i++;
            if(i == maxAttempts) {
                System.out.printf("Gave up generating a new key after %d failed attempts.\n", maxAttempts); // TODO: Add exception?
                return -1;
            }
        } while (v != null);

        return key;
    }

    // return all keys in sorted sequence
    public long[] allKeys() {
        return this.largeData ? this.avl.allKeys() : this.list.allKeys();
    }

    // add an entry for the given key and value
    public String add(long key, String value) {
        String s = this.largeData ? this.avl.put(key, value) : this.list.put(key, value);

        if(s == null) {
            this.incrementCount();
        }

        return s;
    }

    // remove the entry for the given key
    public String remove(long key) {
        String s = this.largeData ? this.avl.remove(key) : this.list.remove(key);

        if(s != null) {
            this.decrementCount();
        }

        return s;
    }

    // return the values of the given key
    public String getValues(long key) {
        return "";
    }

    // return the key for the successor of key
    public int nextKey(long key) {
        return 0;
    }

    // return the key for the predecessor of key
    public int prevKey(long key) {
        return 0;
    }

    // returns the number of keys that are within the specified range of the two keys
    public int rangeKey(long key1, long key2) {
        return 0;
    }
}
