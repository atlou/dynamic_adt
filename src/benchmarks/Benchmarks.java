package benchmarks;

import main.AVLTree;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Benchmarks {
    public static void main(String[] args) {
        AVLTree bst = new AVLTree();
        long startTime = System.currentTimeMillis();
        avlFile("src/NASTA_test_file1.txt", bst);
        long endTime = System.currentTimeMillis();
        System.out.println("500k put(): " + (endTime - startTime) + " milliseconds");

        startTime = System.currentTimeMillis();
        System.out.println(bst.allKeys().length);
        endTime = System.currentTimeMillis();
        System.out.println("500k allKeys(): " + (endTime - startTime) + " milliseconds");
    }

    private static void avlFile(String path, AVLTree bst) {
        Scanner s = null;
        File f = new File(path);
        try {
            s = new Scanner(f);
            while(s.hasNextLine()) {
                String line = s.nextLine();
                bst.put(Long.parseLong(line), line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
