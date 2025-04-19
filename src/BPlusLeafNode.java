package src;
import java.util.*;

import src.BPlusTree.SplitResult;
class BPlusLeafNode extends BPlusNode {
    List<Integer> keys;
    BPlusLeafNode next;

    public BPlusLeafNode() {
        this.keys = new ArrayList<>();
        this.next = null;
    }

    @Override
    SplitResult insert(int key, BPlusTree tree) {
        // Insert the key in sorted order
        int pos = Collections.binarySearch(keys, key);
        if (pos >= 0) return null;  // If the key is already present, return null
        
        // Insert the key at the correct position
        keys.add(-pos - 1, key);
        
        // Handle splitting if the node exceeds the order
        if (keys.size() > tree.order) {
            int mid = keys.size() / 2;
            
            // Create a new leaf node to hold the second half of the keys
            BPlusLeafNode newLeaf = new BPlusLeafNode();
            newLeaf.keys.addAll(keys.subList(mid, keys.size()));
            keys = new ArrayList<>(keys.subList(0, mid));
            
            // Link the current leaf node to the new leaf node
            newLeaf.next = this.next;
            this.next = newLeaf;
            
            // Return the split result (new key, left node, right node)
            int newKey = newLeaf.keys.get(0);  // The first key of the new leaf node becomes the "up" key
            return new SplitResult(newKey, this, newLeaf);  // Return the result for splitting
        }
        
        return null;  // No split occurred
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
            for (int key : current.keys) {
                if (key >= start && key <= end) result.add(key);
                else if (key > end) return result;
            }
            current = current.next;
        }
        return result;
    }

    @Override
    void delete(int key, BPlusTree tree) {
        keys.remove(Integer.valueOf(key));
        // handle underflow and rebalancing
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

