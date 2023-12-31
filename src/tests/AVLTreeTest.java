package tests;

import java.util.Random;
import java.util.Arrays;


import main.AVLTree;
import main.exceptions.KeyNotFoundException;
import main.exceptions.NoNextKeyException;
import main.exceptions.avl.AVLTreeException;
import main.exceptions.avl.NoParentException;
import main.exceptions.avl.NodeIsInternalException;
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
        try {
            bst.remove(4);
            bst.remove(5);
            bst.remove(6);
            bst.remove(40);
        } catch (AVLTreeException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Range: " + bst.rangeKey(0, 100));
        System.out.println("Range: " + bst.rangeKey(4, 6));
        System.out.println("Range: " + bst.rangeKey(7, 10));
    }

    @Test
    void remove() {
        AVLTree bst = new AVLTree();

        for (int i = 1; i <= 10; i++) {
            bst.put(i, Integer.toString(i));
        }

        try {
            bst.remove(4);
            bst.remove(2);
            bst.remove(5);
            bst.remove(10);
            bst.remove(1);
        } catch (AVLTreeException e) {
            System.out.println(e.getMessage());
        }

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

        try {
            Assertions.assertEquals(20, bst.nextKey(10));
            Assertions.assertEquals(30, bst.nextKey(20));
            Assertions.assertEquals(40, bst.nextKey(30));
            Assertions.assertEquals(50, bst.nextKey(40));
            Assertions.assertEquals(-2, bst.nextKey(50));
            Assertions.assertEquals(-2, bst.nextKey(60));
            Assertions.assertEquals(10, bst.nextKey(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void prevKey() {
        AVLTree bst = new AVLTree();
        for (int i = 10; i < 50; i += 10) {
            bst.put(i, Integer.toString(i));
        }

        try {
            Assertions.assertEquals(-2, bst.prevKey(10));
            Assertions.assertEquals(10, bst.prevKey(20));
            Assertions.assertEquals(20, bst.prevKey(30));
            Assertions.assertEquals(30, bst.prevKey(40));
            Assertions.assertEquals(40, bst.prevKey(50));
            Assertions.assertEquals(40, bst.prevKey(60));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}