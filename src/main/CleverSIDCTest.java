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

    }

    @Test
    void nextKey() {
    }

    @Test
    void prevKey() {
    }

    @Test
    void rangeKey() {
    }
}