package src;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Random rand = new Random();

        // (a) Generate 10,000 records
        List<Integer> records = DataGenerator.generateRecords(10000, 100000, 200000);
        System.out.println("Generated 10,000 records.");

        // (b) Build B+ trees
        BPlusTree dense13 = new BPlusTree(13);
        dense13.buildDense(records);
        System.out.println("\nBuilt dense B+ tree of order 13.");

        BPlusTree sparse13 = new BPlusTree(13);
        sparse13.buildSparse(records);
        System.out.println("\nBuilt sparse B+ tree of order 13.");

        BPlusTree dense24 = new BPlusTree(24);
        dense24.buildDense(records);
        System.out.println("\nBuilt dense B+ tree of order 24.");

        BPlusTree sparse24 = new BPlusTree(24);
        sparse24.buildSparse(records);
        System.out.println("\nBuilt sparse B+ tree of order 24.");

        // (c1) 2 insertions on dense trees
        System.out.println("\n(c1) Inserting 2 keys into dense B+ trees...");
        for (int i = 0; i < 2; i++) {
            testInsert(dense13, rand.nextInt(300000));
            testInsert(dense24, rand.nextInt(300000));
        }

        // (c2) 2 deletions on sparse trees
        System.out.println("\n(c2) Deleting 2 keys from sparse B+ trees...");
        for (int i = 0; i < 2; i++) {
            testDelete(sparse13, records.get(rand.nextInt(records.size())));
            testDelete(sparse24, records.get(rand.nextInt(records.size())));
        }

        // (c3) 5 random insert/delete on each tree
        System.out.println("\n(c3) Performing 5 random insert/delete ops on each tree...");
        for (int i = 0; i < 5; i++) {
            if (rand.nextBoolean()) {
                testInsert(dense13, rand.nextInt(300000));
                testInsert(sparse13, rand.nextInt(300000));
            } else {
                testDelete(dense13, records.get(rand.nextInt(records.size())));
                testDelete(sparse13, records.get(rand.nextInt(records.size())));
            }

            if (rand.nextBoolean()) {
                testInsert(dense24, rand.nextInt(300000));
                testInsert(sparse24, rand.nextInt(300000));
            } else {
                testDelete(dense24, records.get(rand.nextInt(records.size())));
                testDelete(sparse24, records.get(rand.nextInt(records.size())));
            }
        }

        // (c4) 5 searches on each tree
        System.out.println("\n(c4) Performing 5 search ops on each tree...");
        for (int i = 0; i < 5; i++) {
            testSearch(dense13, rand.nextInt(300000));
            testSearch(sparse13, rand.nextInt(300000));
            testSearch(dense24, rand.nextInt(300000));
            testSearch(sparse24, rand.nextInt(300000));
        }
    }

    static void testInsert(BPlusTree tree, int key) {
        System.out.println("\nInserting key: " + key);
        System.out.println("Before:");
        tree.printTree();
        tree.insert(key);
        System.out.println("After:");
        tree.printTree();
    }

    static void testDelete(BPlusTree tree, int key) {
        System.out.println("\nDeleting key: " + key);
        System.out.println("Before:");
        tree.printTree();
        tree.delete(key);
        System.out.println("After:");
        tree.printTree();
    }

    static void testSearch(BPlusTree tree, int key) {
        System.out.println("\nSearching for key: " + key);
        boolean found = tree.search(key);
        System.out.println("Found? " + found);
    }
}
