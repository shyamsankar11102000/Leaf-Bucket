package src;

import java.util.*;

import src.BPlusTree.SplitResult;

public class BPlusInternalNode extends BPlusNode {
    List<Integer> keys;
    List<BPlusNode> children;

    public BPlusInternalNode() {
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    @Override
    SplitResult insert(int key, BPlusTree tree) {
        int idx = findChildIndex(key);
        SplitResult result = children.get(idx).insert(key, tree);

        if (result != null) {
            keys.add(idx, result.newKey);
            children.set(idx, result.left);
            children.add(idx + 1, result.right);

            if (keys.size() >= tree.order) {
                int mid = keys.size() / 2;

                BPlusInternalNode rightNode = new BPlusInternalNode();
                rightNode.keys.addAll(keys.subList(mid + 1, keys.size()));
                rightNode.children.addAll(children.subList(mid + 1, children.size()));

                int upKey = keys.get(mid);

                // Shrink current node's keys and children
                keys = new ArrayList<>(keys.subList(0, mid));
                children = new ArrayList<>(children.subList(0, mid + 1));

                return new BPlusTree.SplitResult(upKey, this, rightNode);
            }
        }

        return null;
    }

    private int findChildIndex(int key) {
        int i = 0;
        while (i < keys.size() && key >= keys.get(i)) {
            i++;
        }
        return i;
    }

    @Override
    boolean search(int key) {
        return children.get(findChildIndex(key)).search(key);
    }

    @Override
    List<Integer> rangeSearch(int start, int end) {
        return children.get(findChildIndex(start)).rangeSearch(start, end);
    }

    @Override
    void delete(int key, BPlusTree tree) {
        children.get(findChildIndex(key)).delete(key, tree);
        // Rebalancing or merge logic can go here if needed
    }

    @Override
    void print() {
        System.out.println("Internal: " + keys);
        for (BPlusNode child : children) {
            child.print();
        }
    }

    @Override
    boolean isLeaf() {
        return false;
    }
}
