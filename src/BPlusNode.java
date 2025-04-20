package src;

import java.util.List;
import src.BPlusTree.SplitResult;

public abstract class BPlusNode {
    abstract SplitResult insert(int key, BPlusTree tree);
    abstract boolean search(int key);
    abstract List<Integer> rangeSearch(int start, int end);
    abstract void delete(int key, BPlusTree tree);
    abstract void print();
    abstract boolean isLeaf();
}
