package src;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int order = 4; // You can change to 13 or 24 for experiments
        BPlusTree denseTree = new BPlusTree(order);
        BPlusTree sparseTree = new BPlusTree(order);

        // Generate 10,000 unique keys between 100,000 and 200,000
        List<Integer> records = generateUniqueKeys(10000, 100000, 200000);

        // Build Dense Tree
        System.out.println("=== Building Dense B+ Tree ===");
        denseTree.buildDense(records);
        denseTree.printTree();

        // Build Sparse Tree
        System.out.println("\n=== Building Sparse B+ Tree ===");
        sparseTree.buildSparse(records);
        sparseTree.printTree();

        // Search Test
        System.out.println("\n=== Search Test ===");
        int[] testKeys = {records.get(0), records.get(100), 123456}; // One guaranteed, one random, one maybe not
        for (int key : testKeys) {
            System.out.println("Key " + key + " found in Dense Tree: " + denseTree.search(key));
            System.out.println("Key " + key + " found in Sparse Tree: " + sparseTree.search(key));
        }
    }

    public static List<Integer> generateUniqueKeys(int count, int min, int max) {
        Random rand = new Random();
        Set<Integer> set = new HashSet<>();
        while (set.size() < count) {
            set.add(rand.nextInt(max - min + 1) + min);
        }
        return new ArrayList<>(set);
    }
}

