package tests;

import avl.*;

import java.util.Random;
import java.util.Arrays;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AVLTreeTest {

    @Test
    void allKeys() {
        Random rand = new Random();
        AVLTree bst = new AVLTree();
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
        AVLTree bst = new AVLTree();
        for (int i = 1; i <= 50; i++) {
            bst.put(i, Integer.toString(i));
        }
        System.out.println("Range: " + bst.rangeKey(0, 100));
        bst.remove(4);
        bst.remove(5);
        bst.remove(6);
        bst.remove(40);
        System.out.println("Range: " + bst.rangeKey(0, 100));
    }

    @Test
    void remove() {
        AVLTree bst = new AVLTree();

        for (int i = 1; i <= 10; i++) {
            bst.put(i, Integer.toString(i));
        }

        bst.remove(4);
        bst.remove(2);
        bst.remove(5);
        bst.remove(10);
        bst.remove(1);

        Assertions.assertArrayEquals(new long[]{3, 6, 7, 8, 9}, bst.allKeys());
    }

    @Test
    void put() {
        AVLTree bst = new AVLTree();
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
        AVLTree bst = new AVLTree();
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
        AVLTree bst = new AVLTree();
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
        AVLTree bst = new AVLTree();
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