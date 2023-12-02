package benchmarks;

import main.CleverSIDC;
import java.io.*;
import java.util.Scanner;

public class Benchmarks {
    public static void main(String[] args) {
        try {
            PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream("src/benchmarks/report.txt")), true);
            System.setOut(ps);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Program terminating");
            System.exit(0);
        }

        CleverSIDC c = new CleverSIDC();
        c.setSIDCThreshold(3000);

        System.out.println("---------- REPORT ----------");
        System.out.println("number of keys: " + String.format("%,d", 1500000));
        System.out.println("threshold: " + String.format("%,d", c.getSIDCThreshold()));

        // ADD()
        long startTime = System.currentTimeMillis();
        insertion("src/benchmarks/NASTA_test_file1.txt", c);
        insertion("src/benchmarks/NASTA_test_file2.txt", c);
        insertion("src/benchmarks/NASTA_test_file3.txt", c);
        long endTime = System.currentTimeMillis();
        printReport("add", 1500000, c.getCount(), startTime, endTime);

        // NEXT()
        startTime = System.currentTimeMillis();
        try {
            c.nextKey(90049726);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        printReport("nextKey", 1, c.getCount(), startTime, endTime);

        // PREV()
        startTime = System.currentTimeMillis();
        try {
            c.prevKey(90049726);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        printReport("prevKey", 1, c.getCount(), startTime, endTime);

        // GENERATE()
        startTime = System.currentTimeMillis();
        try {
            c.generate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        printReport("generate", 1, c.getCount(), startTime, endTime);

        // GET VALUES()
        startTime = System.currentTimeMillis();
        c.getValues(90049726);
        endTime = System.currentTimeMillis();
        printReport("getValues", 1, c.getCount(), startTime, endTime);

        // RANGE KEYS()
        startTime = System.currentTimeMillis();
        c.rangeKey(Long.MIN_VALUE, Long.MAX_VALUE);
        endTime = System.currentTimeMillis();
        printReport("rangeKey", 1, c.getCount(), startTime, endTime);

        // ALL KEYS()
        startTime = System.currentTimeMillis();
        c.allKeys();
        endTime = System.currentTimeMillis();
        printReport("allKeys", 1, c.getCount(), startTime, endTime);

        // REMOVE()
        startTime = System.currentTimeMillis();
        try {
            c.remove(90049726);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        printReport("remove", 1, c.getCount(), startTime, endTime);

        // ADD()
        startTime = System.currentTimeMillis();
        try {
            c.add(90049726, "90049726");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        printReport("add", 1, c.getCount(), startTime, endTime);

    }

    private static void insertion(String filePath, CleverSIDC c) {
        Scanner s = null;
        File f = new File(filePath);
        try {
            s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                c.add(Long.parseLong(line), line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Program terminating.");
            System.exit(0);
        }
    }

    private static void printReport(String func, int op, int count, long startTime, long endTime) {
        System.out.printf("---------- %s() ----------\n", func);
        System.out.println("operations: " + String.format("%,d", op));
        System.out.println("count: " + String.format("%,d", count));
        System.out.println("time: " + String.format("%,d", (endTime - startTime)) + " ms");
    }
}
