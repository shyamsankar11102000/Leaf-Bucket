package src;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        // Generate 10,000 unique keys
        List<Integer> keys = DataGenerator.generateRecords(10000, 100000, 200000);

        // Build Dense Tree of Order 13
        System.out.println("----- Dense B+ Tree (Order 13) -----");
        BPlusTree denseTree = new BPlusTree(13);
        denseTree.buildDense(keys);
        denseTree.printTree();

        // Build Sparse Tree of Order 13
        System.out.println("----- Sparse B+ Tree (Order 13) -----");
        BPlusTree sparseTree = new BPlusTree(13);
        sparseTree.buildSparse(keys);
        sparseTree.printTree();
    }
}

