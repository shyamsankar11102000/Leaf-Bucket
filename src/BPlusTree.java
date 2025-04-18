package src;
import java.util.*;

class BPlusTree {
    int order;
    Node root;

    public BPlusTree(int order) {
        this.order = order;
        this.root = new LeafNode();
    }

    // Dense insert: Sorted insertion
    public void buildDense(List<Integer> records) {
        Collections.sort(records); // Sorted for max fill
        for (int key : records) {
            insert(key);
        }
    }

    // Sparse insert: Random insertion order
    public void buildSparse(List<Integer> records) {
        Collections.shuffle(records); // Shuffle to cause unbalanced growth
        for (int key : records) {
            insert(key);
        }
    }

    public void insert(int key) {
        SplitResult result = root.insert(key, order);
        if (result != null) {
            InternalNode newRoot = new InternalNode();
            newRoot.keys.add(result.newKey);
            newRoot.children.add(result.left);
            newRoot.children.add(result.right);
            root = newRoot;
        }
    }

    // Print tree (for debugging)
    public void printTree() {
        root.print("");
    }
    public boolean search(int key) {
        return root.search(key);
    }
    

    // --- Inner Classes Below ---

    abstract class Node {
        abstract SplitResult insert(int key, int order);
        abstract void print(String indent);
    }

    class LeafNode extends Node {
        List<Integer> keys = new ArrayList<>();
        LeafNode next;

        @Override
        SplitResult insert(int key, int order) {
            int pos = Collections.binarySearch(keys, key);
            if (pos >= 0) return null; // Duplicate, ignore

            keys.add(-pos - 1, key);

            if (keys.size() > order) {
                int mid = keys.size() / 2;
                LeafNode rightNode = new LeafNode();
                rightNode.keys.addAll(keys.subList(mid, keys.size()));
                keys = new ArrayList<>(keys.subList(0, mid));

                rightNode.next = this.next;
                this.next = rightNode;

                return new SplitResult(rightNode.keys.get(0), this, rightNode);
            }

            return null;
        }

        @Override
        void print(String indent) {
            System.out.println(indent + "Leaf: " + keys);
        }
    }

    class InternalNode extends Node {
        List<Integer> keys = new ArrayList<>();
        List<Node> children = new ArrayList<>();

        @Override
        SplitResult insert(int key, int order) {
            int idx = findChildIndex(key);
            SplitResult result = children.get(idx).insert(key, order);

            if (result != null) {
                keys.add(idx, result.newKey);
                children.set(idx, result.left);
                children.add(idx + 1, result.right);

                if (keys.size() >= order) {
                    int mid = keys.size() / 2;
                    InternalNode rightNode = new InternalNode();

                    rightNode.keys.addAll(keys.subList(mid + 1, keys.size()));
                    rightNode.children.addAll(children.subList(mid + 1, children.size()));

                    int upKey = keys.get(mid);

                    keys = new ArrayList<>(keys.subList(0, mid));
                    children = new ArrayList<>(children.subList(0, mid + 1));

                    return new SplitResult(upKey, this, rightNode);
                }
            }

            return null;
        }

        private int findChildIndex(int key) {
            int i = 0;
            while (i < keys.size() && key >= keys.get(i)) i++;
            return i;
        }

        @Override
        void print(String indent) {
            System.out.println(indent + "Internal: " + keys);
            for (Node child : children) {
                child.print(indent + "  ");
            }
        }
    }

    class SplitResult {
        int newKey;
        Node left;
        Node right;

        SplitResult(int newKey, Node left, Node right) {
            this.newKey = newKey;
            this.left = left;
            this.right = right;
        }
    }
}
