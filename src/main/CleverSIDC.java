package main;

import main.exceptions.avl.*;
import main.exceptions.*;

import java.util.concurrent.ThreadLocalRandom;

public class CleverSIDC {

    private boolean largeData; // true when N has reached threshold, false otherwise
    private int threshold; // number at which the data switches its underlying adt
    private int count; // amount of data
    private LinkedList list; // doubly linked list, used for small data amounts
    private AVLTree avl; // avl tree, used for large data amounts

    public CleverSIDC() {
        this.largeData = false;
        this.threshold = 10000; // TODO: Find best threshold
        this.count = 0;
        this.list = new LinkedList();
        this.avl = new AVLTree();
    }

    //------------ DYNAMIC ADT SWITCHING -------------//
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
        // switch to large data
        if(this.count >= this.threshold && !this.largeData) {
            this.largeData = true;
            this.listToAVL();
        }
        // large data goes under threshold
        // switch to small data
        if(this.count < this.threshold && this.largeData) {
            this.largeData = false;
            this.avlToList();
        }
    }

    private void listToAVL() {
//        System.out.println("Switching from Linked List to AVL Tree");
        long[] keys = this.list.allKeys();
        String[] data = this.list.allData();

        for(int i = 0; i < keys.length; i++) {
            this.avl.put(keys[i], data[i]);
        }

        this.list = new LinkedList();
    }

    private void avlToList() {
//        System.out.println("Switching from AVL Tree to Linked List");
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
        this.updateMode();
    }

    public int getSIDCThreshold() {
        return this.threshold;
    }

    public int getCount() {
        return this.count;
    }

    //------------ ADT METHODS -------------//

    // randomly generates new non-existing key of 8 digits
    // bruteforce a new key
    public long generate() throws TooManyAttemptsException {
        int maxAttempts = 10000;
        long min = 10000000;
        long max = 100000000;
        long key;
        String v;
        int i = 0;
        do {
            key = ThreadLocalRandom.current().nextLong(min, max);
            v = this.largeData ? this.avl.getValue(key) : this.list.get(key);
            i++;
            if(i == maxAttempts) {
                throw new TooManyAttemptsException(String.format("Gave up generating a new key after %d failed attempts.", maxAttempts));
            }
        } while (v != null);

        return key;
    }

    // return all keys in sorted sequence (array)
    public long[] allKeys() {
        return this.largeData ? this.avl.allKeys() : this.list.allKeys();
    }

    // add an entry for the given key and value
    // if the key existed, its value is replaced and the old value is returned
    public String add(long key, String value) {
        String s = this.largeData ? this.avl.put(key, value) : this.list.put(key, value);

        if(s == null) {
            // key does not already exist
            this.incrementCount();
        }

        return s;
    }

    // remove the entry for the given key
    // if the key existed, its value is returned
    public String remove(long key) throws NodeIsInternalException, NoParentException {
        String s = this.largeData ? this.avl.remove(key) : this.list.remove(key);

        if(s != null) {
            // a key was removed
            this.decrementCount();
        }

        return s;
    }

    // return the value of the given key
    public String getValues(long key) {
        return this.largeData ? this.avl.getValue(key) : this.list.get(key);
    }

    // return the key for the successor of key
    // i.e. the smallest key that is greater than the current key
    public long nextKey(long key) throws KeyNotFoundException, NoNextKeyException {
        return this.largeData ? this.avl.nextKey(key) : this.list.nextKey(key);
    }

    // return the key for the predecessor of key
    // i.e. the biggest key that is smaller than the current key
    public long prevKey(long key) throws KeyNotFoundException, NoPrevKeyException {
        return this.largeData ? this.avl.prevKey(key) : this.list.prevKey(key);
    }

    // returns the number of keys that are within the specified range of the two keys
    // includes the bounds
    public int rangeKey(long key1, long key2) {
        return this.largeData ? this.avl.rangeKey(key1, key2) : this.list.rangeKey(key1, key2);
    }
}
