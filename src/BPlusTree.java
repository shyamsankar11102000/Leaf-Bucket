package src;

import java.util.*;

public class BPlusTree {
    public int order;
    public BPlusNode root;

    public BPlusTree(int order) {
        this.order = order;
        this.root = new BPlusLeafNode();
    }

    public void buildDense(List<Integer> records) {
        Collections.sort(records);
        for (int key : records) {
            insert(key);
        }
    }

    public void buildSparse(List<Integer> records) {
        Collections.shuffle(records);
        for (int key : records) {
            insert(key);
        }
    }

    public void insert(int key) {
        SplitResult result = root.insert(key, this);
        if (result != null) {
            BPlusInternalNode newRoot = new BPlusInternalNode();
            newRoot.keys.add(result.newKey);
            newRoot.children.add(result.left);
            newRoot.children.add(result.right);
            root = newRoot;
        }
    }

    public boolean search(int key) {
        return root.search(key);
    }

    public List<Integer> rangeSearch(int start, int end) {
        return root.rangeSearch(start, end);
    }

    public void delete(int key) {
        root.delete(key, this);
        // Optional: root merge handling can be added here
    }

    public void printTree() {
        root.print();
    }

    // Inner class to represent the result of a split
    public static class SplitResult {
        public int newKey;
        public BPlusNode left;
        public BPlusNode right;

        public SplitResult(int newKey, BPlusNode left, BPlusNode right) {
            this.newKey = newKey;
            this.left = left;
            this.right = right;
        }
    }
}
