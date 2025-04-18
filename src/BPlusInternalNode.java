class BPlusInternalNode extends BPlusNode {
    List<Integer> keys;
    List<BPlusNode> children;

    public BPlusInternalNode() {
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    @Override
    void insert(int key, BPlusTree tree) {
        // Navigate to child and delegate insert
        // Split if needed and propagate changes upward
    }

    @Override
    boolean search(int key) {
        // Navigate down to leaf node
        return getChild(key).search(key);
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
