public class Main {


    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.put(1, "a");
        tree.put(-10, "a");
        tree.put(-1, "a");
        tree.put(2, "b");
        tree.put(6, "b");
        tree.put(1, "b");
        tree.put(9, "b");
        tree.put(3, "c");
        tree.put(4, "d");
        tree.put(5, "e");
        tree.put(5, "e");
        tree.put(5, "e");

        tree.allKeys();

        System.out.println("3: " + tree.get(3));
        System.out.println("4: " + tree.get(4));
        System.out.println("20: " + tree.get(20));
    }
}
