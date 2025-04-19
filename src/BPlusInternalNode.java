package src;
import java.util.*;

import src.BPlusTree.InternalNode;
import src.BPlusTree.SplitResult;
class BPlusInternalNode extends BPlusNode {
    List<Integer> keys;
    List<BPlusNode> children;

    public BPlusInternalNode() {
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    @Override
SplitResult insert(int key, BPlusTree tree) {
    // Find the appropriate child to insert the key into
    int idx = findChildIndex(key);
    SplitResult result = children.get(idx).insert(key, tree);  // Recursively call insert on the child node

    if (result != null) {
        // If there was a split in the child, handle the split
        keys.add(idx, result.newKey);  // Insert the new key returned from the child split
        children.set(idx, result.left);  // Update the left child of this internal node
        children.add(idx + 1, result.right);  // Add the right child to the children list

        // Check if the internal node is full and needs to be split
        if (keys.size() >= tree.order) {
            int mid = keys.size() / 2;

            // Create a new internal node to hold the second half of the keys and children
            InternalNode newInternalNode = new InternalNode();
            newInternalNode.keys.addAll(keys.subList(mid + 1, keys.size()));
            newInternalNode.children.addAll(children.subList(mid + 1, children.size()));

            // The middle key is the "up" key that will be pushed up to the parent
            int upKey = keys.get(mid);

            // Update this internal node to hold only the first half
            keys = new ArrayList<>(keys.subList(0, mid));
            children = new ArrayList<>(children.subList(0, mid + 1));

            // Return the split result
            return new SplitResult(upKey, this, newInternalNode);  // Return the split result
        }
    }

    return null;  // No split occurred
}

private int findChildIndex(int key) {
    // Find the index of the child that should handle this key
    int i = 0;
    while (i < keys.size() && key >= keys.get(i)) {
        i++;
    }
    return i;
}

    @Override
    boolean search(int key) {
    int idx = findChildIndex(key);
    return children.get (idx).search(key);
    }
    @Override
    List<Integer> rangeSearch(int start, int end) {
        return getChild(start).rangeSearch(start, end);
    }

    @Override
    void delete(int key, BPlusTree tree) {
        getChild(key).delete(key, tree);
        // Handle underflow if needed
    }

    BPlusNode getChild(int key) {
        int idx = 0;
        while (idx < keys.size() && key >= keys.get(idx)) idx++;
        return children.get(idx);
    }

    @Override
    void print() {
        System.out.println("Internal: " + keys);
    }

    @Override
    boolean isLeaf() {
        return false;
    }
}
