package src;
import java.util.*;

class BPlusTree {
    int order;
    BPlusNode root;

    public BPlusTree(int order) {
        this.order = order;
        this.root = new BPlusLeafNode();
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
        SplitResult result = root.insert(key, this);  // Pass the tree as context
        if (result != null) {
            // If there is a split, create a new root
            InternalNode newRoot = new InternalNode();
            newRoot.keys.add(result.newKey);  // Add the "up" key to the internal node
            newRoot.children.add(result.left);  // Add the left child
            newRoot.children.add(result.right);  // Add the right child
            root = newRoot;  // Set the new internal node as the root
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

    abstract class BPlusNode {
        abstract SplitResult insert(int key, BPlusTree tree);
        abstract void print(String indent);
    }

    class BPlusLeafNode extends BPlusNode {
        List<Integer> keys = new ArrayList<>();
        BPlusLeafNode next;

        @Override
        SplitResult insert(int key, BPlusTree tree) {
            int pos = Collections.binarySearch(keys, key);
            if (pos >= 0) return null; // Duplicate, ignore

            keys.add(-pos - 1, key);

            if (keys.size() > tree.order) {
                int mid = keys.size() / 2;
                BPlusLeafNode rightNode = new BPlusLeafNode();
                rightNode.keys.addAll(keys.subList(mid, keys.size()));
                keys = new ArrayList<>(keys.subList(0, mid));

                rightNode.next = this.next;
                this.next = rightNode;

                return new SplitResult(rightNode.keys.get(0), this, rightNode); // Passing BPlusNode instead of BPlusLeafNode
            }

            return null;
        }

        @Override
        void print(String indent) {
            System.out.println(indent + "Leaf: " + keys);
        }

        boolean search(int key) {
            return keys.contains(key);
        }
    }

    class InternalNode extends BPlusNode {
        List<Integer> keys = new ArrayList<>();
        List<BPlusNode> children = new ArrayList<>();

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
                    InternalNode rightNode = new InternalNode();

                    rightNode.keys.addAll(keys.subList(mid + 1, keys.size()));
                    rightNode.children.addAll(children.subList(mid + 1, children.size()));

                    int upKey = keys.get(mid);

                    keys = new ArrayList<>(keys.subList(0, mid));
                    children = new ArrayList<>(children.subList(0, mid + 1));

                    return new SplitResult(upKey, this, rightNode);  // Returning BPlusNode here too
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
            for (BPlusNode child : children) {
                child.print(indent + "  ");
            }
        }
    }

    // --- SplitResult Class ---

    class SplitResult {
        int newKey;
        BPlusNode left;
        BPlusNode right;

        // Constructor for SplitResult
        SplitResult(int newKey, BPlusNode left, BPlusNode right) {
            this.newKey = newKey;
            this.left = left;
            this.right = right;
        }
    }
}
