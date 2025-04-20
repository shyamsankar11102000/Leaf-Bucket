package src;

import java.util.*;

import src.BPlusTree.SplitResult;

public class BPlusLeafNode extends BPlusNode {
    List<Integer> keys;
    BPlusLeafNode next;

    public BPlusLeafNode() {
        this.keys = new ArrayList<>();
        this.next = null;
    }

    @Override
    SplitResult insert(int key, BPlusTree tree) {
        int pos = Collections.binarySearch(keys, key);
        if (pos >= 0) return null; // Duplicate key, no insertion

        keys.add(-pos - 1, key); // Insert at the correct sorted position

        // If overflow, split
        if (keys.size() > tree.order) {
            int mid = keys.size() / 2;

            BPlusLeafNode rightNode = new BPlusLeafNode();
            rightNode.keys.addAll(keys.subList(mid, keys.size()));
            keys = new ArrayList<>(keys.subList(0, mid));

            rightNode.next = this.next;
            this.next = rightNode;

            int newKey = rightNode.keys.get(0); // First key of the right node
            return new BPlusTree.SplitResult(newKey, this, rightNode); // Return as BPlusNode
        }

        return null; // No split
    }

    @Override
    boolean search(int key) {
        return keys.contains(key);
    }

    @Override
    List<Integer> rangeSearch(int start, int end) {
        List<Integer> result = new ArrayList<>();
        BPlusLeafNode current = this;

        while (current != null) {
            for (int k : current.keys) {
                if (k >= start && k <= end) result.add(k);
                else if (k > end) return result;
            }
            current = current.next;
        }

        return result;
    }

    @Override
    void delete(int key, BPlusTree tree) {
        keys.remove(Integer.valueOf(key));
        // Rebalancing or merge logic can go here if needed
    }

    @Override
    void print() {
        System.out.println("Leaf: " + keys);
    }

    @Override
    boolean isLeaf() {
        return true;
    }
}
