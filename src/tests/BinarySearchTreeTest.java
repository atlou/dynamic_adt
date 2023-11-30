package tests;

import bst.*;

import java.util.Random;
import java.util.Arrays;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BinarySearchTreeTest {

    @Test
    void allKeys() {
        Random rand = new Random();
        BinarySearchTree bst = new BinarySearchTree();
        long[] keys = new long[40];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = rand.nextLong();
            bst.put(keys[i], Long.toString(keys[i]));
        }

        Arrays.sort(keys);
        Assertions.assertArrayEquals(keys, bst.allKeys());
    }

    @Test
    void rangeKey() {
    }

    @Test
    void remove() {
        BinarySearchTree bst = new BinarySearchTree();

        for (int i = 1; i <= 10; i++) {
            bst.put(i, Integer.toString(i));
        }
        long[] keys = bst.allKeys();
        System.out.println(Arrays.toString(keys));
        Assertions.assertArrayEquals(new long[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, keys);

        bst.remove(1);
        keys = bst.allKeys();
        System.out.println(Arrays.toString(keys));
        Assertions.assertArrayEquals(new long[]{2, 3, 4, 5, 6, 7, 8, 9, 10}, keys);

        bst.remove(3);
        keys = bst.allKeys();
        System.out.println(Arrays.toString(keys));
        Assertions.assertArrayEquals(new long[]{2, 4, 5, 6, 7, 8, 9, 10}, keys);
    }

    @Test
    void put() {
        BinarySearchTree bst = new BinarySearchTree();
        for (int i = 10; i <= 50; i += 10) {
            bst.put(i, Integer.toString(i));
        }
        long[] keys = bst.allKeys();
        System.out.println(Arrays.toString(keys));
        Assertions.assertArrayEquals(new long[]{10, 20, 30, 40, 50}, keys);
        for (int i = 10; i <= 50; i += 10) {
            bst.put(i, Integer.toString(i));
        }
        keys = bst.allKeys();
        System.out.println(Arrays.toString(keys));
        Assertions.assertArrayEquals(new long[]{10, 20, 30, 40, 50}, keys);
        for (int i = 1; i <= 5; i++) {
            bst.put(i, Integer.toString(i));
        }
        keys = bst.allKeys();
        System.out.println(Arrays.toString(keys));
        Assertions.assertArrayEquals(new long[]{1, 2, 3, 4, 5, 10, 20, 30, 40, 50}, keys);
    }

    @Test
    void getValue() {
        BinarySearchTree bst = new BinarySearchTree();
        long[] keys = {10, 20, 30, 40, 50, 60, 70, 10, 40, 31, 23};

        for (long k : keys) {
            bst.put(k, Long.toString(k));
        }
        for (long k : keys) {
            Assertions.assertEquals(Long.toString(k), bst.getValue(k));
        }

        for (long k : keys) {
            bst.put(k, "abc");
        }
        for (long k : keys) {
            Assertions.assertEquals("abc", bst.getValue(k));
        }
    }

    @Test
    void nextKey() {
        BinarySearchTree bst = new BinarySearchTree();
        for (int i = 10; i <= 50; i += 10) {
            bst.put(i, Integer.toString(i));
        }
        long a = bst.nextKey(10);
        long b = bst.nextKey(20);
        long c = bst.nextKey(30);
        long d = bst.nextKey(40);
        long e = bst.nextKey(50);
        long f = bst.nextKey(60);
        long g = bst.nextKey(0);

        Assertions.assertEquals(20, a);
        Assertions.assertEquals(30, b);
        Assertions.assertEquals(40, c);
        Assertions.assertEquals(50, d);
        Assertions.assertEquals(-2, e);
        Assertions.assertEquals(-2, f);
        Assertions.assertEquals(10, g);
    }

    @Test
    void prevKey() {
        BinarySearchTree bst = new BinarySearchTree();
        for (int i = 10; i < 50; i += 10) {
            bst.put(i, Integer.toString(i));
        }
        long a = bst.prevKey(10);
        long b = bst.prevKey(20);
        long c = bst.prevKey(30);
        long d = bst.prevKey(40);
        long e = bst.prevKey(50);
        long f = bst.prevKey(60);

        Assertions.assertEquals(-2, a);
        Assertions.assertEquals(10, b);
        Assertions.assertEquals(20, c);
        Assertions.assertEquals(30, d);
        Assertions.assertEquals(40, e);
        Assertions.assertEquals(40, f);
    }
}