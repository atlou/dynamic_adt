package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

//        for(int i = 49; i >= 0; i--) {
//            c.remove(i);
//        }
//        Assertions.assertArrayEquals(arr1, c.allKeys());
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