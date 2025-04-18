package src;

class BPlusLeafNode extends BPlusNode {
    List<Integer> keys;
    BPlusLeafNode next;

    public BPlusLeafNode() {
        this.keys = new ArrayList<>();
        this.next = null;
    }

    @Override
    void insert(int key, BPlusTree tree) {
        keys.add(key);
        Collections.sort(keys);
        // handle splitting if keys.size() > order
        // update parent pointers if needed
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

