package Bucket;

import java.util.*;

public class VirtualMemory {
    // Using a Queue to enforce FIFO behavior naturally
    private Queue<List<Tuple>> memory;
    private final int MEMORY_CAPACITY = 100;

    public VirtualMemory() {
        memory = new LinkedList<>();
    }

    // Read a block at a specific index (convert queue to list temporarily)
    public List<Tuple> getBlock(int blockIndex) {
        if (blockIndex >= 0 && blockIndex < memory.size()) {
            return new ArrayList<>(memory).get(blockIndex);
        }
        return null;
    }

    // Add a block to memory with FIFO eviction
    public void loadBlock(List<Tuple> block) {
        if (memory.size() >= MEMORY_CAPACITY) {
            memory.poll(); // Remove the oldest block
        }
        memory.offer(block);
    }

    // Alias for loadBlock (same behavior)
    public void writeBlock(List<Tuple> block) {
        loadBlock(block);
    }

    public boolean isBlockInMemory(List<Tuple> block) {
        return memory.contains(block);
    }

    public int getNumBlocks() {
        return memory.size();
    }

    public void clear() {
        memory.clear();
    }

    // Optional debug method
    public void printMemoryState() {
        int i = 0;
        for (List<Tuple> block : memory) {
            System.out.println("Block " + (i++) + ": " + block);
        }
    }
}
