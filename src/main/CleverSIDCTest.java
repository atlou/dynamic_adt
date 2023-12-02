package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CleverSIDCTest {

    @Test
    void setSIDCThreshold() {
    }

    @Test
    void generate() {
        CleverSIDC c = new CleverSIDC();
        for(int i = 0; i < 10; i++) {
            long key = c.generate();
            c.add(key, Long.toString(key));
        }

        System.out.println(Arrays.toString(c.allKeys()));
    }

    @Test
    void allKeys() {
        CleverSIDC c = new CleverSIDC();
        c.setSIDCThreshold(30);

        long[] arr1 = new long[10];
        long[] arr2 = new long[50];
        for (int i = 0; i < 10; i++) {
            c.add(i, Integer.toString(i));
            arr1[i] = i;
            arr2[i] = i;
        }
        Assertions.assertArrayEquals(arr1, c.allKeys());

        for (int i = 10; i < 50; i++) {
            c.add(i, Integer.toString(i));
            arr2[i] = i;
        }
        Assertions.assertArrayEquals(arr2, c.allKeys());

        for (int i = 49; i >= 10; i--) {
            c.remove(i);
        }
        Assertions.assertArrayEquals(arr1, c.allKeys());
    }

    @Test
    void add() {
        CleverSIDC c = new CleverSIDC();
        c.setSIDCThreshold(30);

        for (int i = 0; i < 100; i++) {
            c.add(i, Integer.toString(i));
        }
    }

    @Test
    void remove() {
        CleverSIDC c = new CleverSIDC();
        c.setSIDCThreshold(5);

        for (int i = 0; i < 4; i++) {
            c.add(i, Integer.toString(i));
        }
        c.add(4, Integer.toString(4));
        c.add(5, Integer.toString(5));
        c.remove(1);
        Assertions.assertArrayEquals(new long[]{0, 2, 3, 4, 5}, c.allKeys());
        c.remove(2);
        Assertions.assertArrayEquals(new long[]{0, 3, 4, 5}, c.allKeys());
        c.remove(3);
        Assertions.assertArrayEquals(new long[]{0, 4, 5}, c.allKeys());
        c.remove(0);
        Assertions.assertArrayEquals(new long[]{4, 5}, c.allKeys());
        c.remove(4);
        Assertions.assertArrayEquals(new long[]{5}, c.allKeys());
        c.remove(5);
        Assertions.assertArrayEquals(new long[]{}, c.allKeys());
    }

    @Test
    void getValues() {
        CleverSIDC c = new CleverSIDC();
        c.setSIDCThreshold(5);

        for (int i = 0; i < 4; i++) {
            c.add(i, Integer.toString(i));
        }

        Assertions.assertEquals("0", c.getValues(0));
        Assertions.assertEquals("2", c.getValues(2));
        Assertions.assertEquals("3", c.getValues(3));
        Assertions.assertEquals(null, c.getValues(10));

        for (int i = 4; i < 10; i++) {
            c.add(i, Integer.toString(i));
        }

        Assertions.assertEquals("0", c.getValues(0));
        Assertions.assertEquals("2", c.getValues(2));
        Assertions.assertEquals("4", c.getValues(4));
        Assertions.assertEquals("8", c.getValues(8));
        Assertions.assertEquals(null, c.getValues(10));

        for (int i = 9; i >= 3; i--) {
            c.remove(i);
        }

        Assertions.assertEquals("0", c.getValues(0));
        Assertions.assertEquals("2", c.getValues(2));
        Assertions.assertEquals(null, c.getValues(4));
        Assertions.assertEquals(null, c.getValues(8));
        Assertions.assertEquals(null, c.getValues(10));
    }

    @Test
    void nextKey() {
        CleverSIDC c = new CleverSIDC();
        c.setSIDCThreshold(5);

        for (int i = 0; i < 4; i++) {
            c.add(i, Integer.toString(i));
        }

        Assertions.assertEquals(1, c.nextKey(0));
        Assertions.assertEquals(3, c.nextKey(2));
        Assertions.assertEquals(-2, c.nextKey(3));

        for (int i = 4; i < 10; i++) {
            c.add(i, Integer.toString(i));
        }

        Assertions.assertEquals(1, c.nextKey(0));
        Assertions.assertEquals(3, c.nextKey(2));
        Assertions.assertEquals(5, c.nextKey(4));
        Assertions.assertEquals(9, c.nextKey(8));
        Assertions.assertEquals(-2, c.nextKey(9));

        for (int i = 9; i >= 3; i--) {
            c.remove(i);
        }

        Assertions.assertEquals(1, c.nextKey(0));
        Assertions.assertEquals(2, c.nextKey(1));
        Assertions.assertEquals(-2, c.nextKey(2));
        Assertions.assertEquals(-2, c.nextKey(4));
        Assertions.assertEquals(-2, c.nextKey(8));
        Assertions.assertEquals(-2, c.nextKey(10));
    }

    @Test
    void prevKey() {
        CleverSIDC c = new CleverSIDC();
        c.setSIDCThreshold(5);

        for (int i = 0; i < 4; i++) {
            c.add(i, Integer.toString(i));
        }

        Assertions.assertEquals(0, c.prevKey(1));
        Assertions.assertEquals(1, c.prevKey(2));
        Assertions.assertEquals(2, c.prevKey(3));
        Assertions.assertEquals(-2, c.prevKey(0));

        for (int i = 4; i < 10; i++) {
            c.add(i, Integer.toString(i));
        }

        Assertions.assertEquals(-2, c.prevKey(0));
        Assertions.assertEquals(1, c.prevKey(2));
        Assertions.assertEquals(3, c.prevKey(4));
        Assertions.assertEquals(7, c.prevKey(8));
        Assertions.assertEquals(8, c.prevKey(9));
        Assertions.assertEquals(-2, c.prevKey(10));

        for (int i = 9; i >= 3; i--) {
            c.remove(i);
        }

        Assertions.assertEquals(-2, c.prevKey(0));
        Assertions.assertEquals(0, c.prevKey(1));
        Assertions.assertEquals(1, c.prevKey(2));
        Assertions.assertEquals(-2, c.prevKey(4));
        Assertions.assertEquals(-2, c.prevKey(8));
        Assertions.assertEquals(-2, c.prevKey(10));
    }

    @Test
    void rangeKey() {
        CleverSIDC c = new CleverSIDC();
        c.setSIDCThreshold(5);

        for (int i = 0; i < 4; i++) {
            c.add(i, Integer.toString(i));
        }

        Assertions.assertEquals(4, c.rangeKey(0, 3));
        Assertions.assertEquals(2, c.rangeKey(0, 1));
        Assertions.assertEquals(2, c.rangeKey(-1, 1));
        Assertions.assertEquals(4, c.rangeKey(-1, 4));
        Assertions.assertEquals(2, c.rangeKey(2, 1));

        for (int i = 4; i < 10; i++) {
            c.add(i, Integer.toString(i));
        }

        Assertions.assertEquals(4, c.rangeKey(0, 3));
        Assertions.assertEquals(2, c.rangeKey(0, 1));
        Assertions.assertEquals(2, c.rangeKey(-1, 1));
        Assertions.assertEquals(5, c.rangeKey(-1, 4));
        Assertions.assertEquals(2, c.rangeKey(2, 1));
        Assertions.assertEquals(10, c.rangeKey(0, 10));
        Assertions.assertEquals(10, c.rangeKey(0, 9));
        Assertions.assertEquals(10, c.rangeKey(-10, 10));

        for (int i = 9; i >= 3; i--) {
            c.remove(i);
        }

        Assertions.assertEquals(3, c.rangeKey(0, 3));
        Assertions.assertEquals(2, c.rangeKey(0, 1));
        Assertions.assertEquals(2, c.rangeKey(-1, 1));
        Assertions.assertEquals(3, c.rangeKey(-1, 4));
        Assertions.assertEquals(2, c.rangeKey(2, 1));
        Assertions.assertEquals(3, c.rangeKey(0, 10));
        Assertions.assertEquals(3, c.rangeKey(0, 9));
        Assertions.assertEquals(3, c.rangeKey(-10, 10));
    }
}