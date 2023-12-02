package tests;

import main.LinkedList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LinkedListTest {

    @Test
    void remove() {
        LinkedList list = new LinkedList();

        long[] keys = new long[100];

        for (int i = 0; i < 100; i++) {
            list.put(i, Integer.toString(i));
        }

        for (int i = 0; i < 100; i++) {
            list.remove(i);
        }

        Assertions.assertArrayEquals(new long[0], list.allKeys());

        for (int i = 0; i < 10; i++) {
            list.put(i, Integer.toString(i));
        }

        list.remove(4);
        Assertions.assertArrayEquals(new long[]{0, 1, 2, 3, 5, 6, 7, 8, 9}, list.allKeys());

        list.remove(0);
        Assertions.assertArrayEquals(new long[]{1, 2, 3, 5, 6, 7, 8, 9}, list.allKeys());

        list.remove(9);
        Assertions.assertArrayEquals(new long[]{1, 2, 3, 5, 6, 7, 8}, list.allKeys());

        list.remove(4);
        Assertions.assertArrayEquals(new long[]{1, 2, 3, 5, 6, 7, 8}, list.allKeys());
    }

    @Test
    void put() {
        LinkedList list = new LinkedList();

        long[] keys = new long[100];

        for (int i = 0; i < 100; i++) {
            keys[i] = i;
        }

        for (int i = 99; i >= 0; i--) {
            list.put(i, Integer.toString(i));
        }

        Assertions.assertArrayEquals(keys, list.allKeys());
    }

    @Test
    void get() {
        LinkedList list = new LinkedList();

        for (int i = 0; i < 100; i += 5) {
            list.put(i, Integer.toString(i));
        }

        Assertions.assertEquals("90", list.get(90));
        Assertions.assertEquals("0", list.get(0));
        Assertions.assertEquals(null, list.get(200));
        Assertions.assertEquals(null, list.get(8));
    }

    @Test
    void prevKey() {
        LinkedList list = new LinkedList();

        for (int i = 0; i < 100; i += 5) {
            list.put(i, Integer.toString(i));
        }

        try {
            Assertions.assertEquals(30, list.prevKey(35));
            Assertions.assertEquals(25, list.prevKey(30));
            Assertions.assertEquals(0, list.prevKey(5));
            Assertions.assertEquals(-1, list.prevKey(0));
            Assertions.assertEquals(-1, list.prevKey(200));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void nextKey() {
        LinkedList list = new LinkedList();

        for (int i = 0; i < 100; i += 5) {
            list.put(i, Integer.toString(i));
        }

        try {
            Assertions.assertEquals(30, list.nextKey(25));
            Assertions.assertEquals(25, list.nextKey(20));
            Assertions.assertEquals(5, list.nextKey(0));
            Assertions.assertEquals(-1, list.nextKey(100));
            Assertions.assertEquals(-1, list.nextKey(-5));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void rangeKey() {
        LinkedList list = new LinkedList();

        for (int i = 0; i < 100; i++) {
            list.put(i, Integer.toString(i));
        }

        Assertions.assertEquals(1, list.rangeKey(-10, 0));
        Assertions.assertEquals(11, list.rangeKey(0, 10));
        Assertions.assertEquals(11, list.rangeKey(20, 30));
        Assertions.assertEquals(100, list.rangeKey(100, -100));
    }

    @Test
    void allKeys() {
        LinkedList list = new LinkedList();

        long[] keys = new long[100];

        for (int i = 0; i < 100; i++) {
            list.put(i, Integer.toString(i));
            keys[i] = i;
        }

        Assertions.assertArrayEquals(keys, list.allKeys());
    }

}