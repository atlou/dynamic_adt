import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {

    @Test
    void remove() {
        LinkedList list = new LinkedList();

        for(int i = 0; i < 10; i++) {
            list.put(i, Integer.toString(i));
        }

        String s = list.remove(4);
        Assertions.assertEquals(s, "4");

        for(int i = 0; i < 10; i++) {
            list.remove(i);
        }

        Assertions.assertEquals("", list.allKeys());
    }

    @Test
    void put() {
        LinkedList list = new LinkedList();

        for(int i = 0; i < 10; i++) {
            list.put(i, Integer.toString(i));
        }

        Assertions.assertEquals("9, 8, 7, 6, 5, 4, 3, 2, 1, 0, ", list.allKeys());
    }

    @Test
    void insertSorted() {
        LinkedList list = new LinkedList();
        ArrayList<Long> keys = new ArrayList<>();

        for(int i = 100; i > 0; i--) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
            list.insertSorted(randomNum, Integer.toString(i));
            keys.add((long) randomNum);
        }

        System.out.println(list.allKeys());
    }

    @Test
    void get() {
        LinkedList list = new LinkedList();

        for(int i = 0; i < 100; i += 5) {
            list.insertSorted(i, Integer.toString(i));
        }

        Assertions.assertEquals("90", list.get(90));
        Assertions.assertEquals("0", list.get(0));
        Assertions.assertEquals(null, list.get(200));
        Assertions.assertEquals(null, list.get(8));
    }

    @Test
    void prevKey() {
        LinkedList list = new LinkedList();

        for(int i = 0; i < 100; i += 5) {
            list.insertSorted(i, Integer.toString(i));
        }

        Assertions.assertEquals(30, list.prevKey(35));
        Assertions.assertEquals(25, list.prevKey(30));
        Assertions.assertEquals(0, list.prevKey(5));
        Assertions.assertEquals(-1, list.prevKey(0));
        Assertions.assertEquals(-1, list.prevKey(200));
    }

    @Test
    void nextKey() {
        LinkedList list = new LinkedList();

        for(int i = 0; i < 100; i += 5) {
            list.insertSorted(i, Integer.toString(i));
        }

        Assertions.assertEquals(30, list.nextKey(25));
        Assertions.assertEquals(25, list.nextKey(20));
        Assertions.assertEquals(5, list.nextKey(0));
        Assertions.assertEquals(-1, list.nextKey(100));
        Assertions.assertEquals(-1, list.nextKey(-5));
    }

}