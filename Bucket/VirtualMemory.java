package Bucket;

import java.util.*;

public class VirtualMemory {
    // ArrayList to simulate the virtual memory; can hold 15 blocks as per the problem description
    private List<List<Tuple>> memory;
    private final int MEMORY_CAPACITY = 15;

    // Constructor
    public VirtualMemory() {
        memory = new ArrayList<>();
    }

    // Read a block from virtual memory
    public List<Tuple> readBlock(int blockIndex) {
        if (blockIndex >= 0 && blockIndex < memory.size()) {
            return memory.get(blockIndex); // Return the block if it's within memory bounds
        }
        return null; // Block index is out of bounds
    }

    // Write a block to virtual memory
    public void writeBlock(List<Tuple> block) {
        if (memory.size() >= MEMORY_CAPACITY) {
            memory.remove(0); // Remove the oldest block if memory is full (FIFO strategy)
        }
        memory.add(block); // Add the new block
    }

    // Check if the block is in memory
    public boolean isBlockInMemory(int blockIndex) {
        return blockIndex >= 0 && blockIndex < memory.size();
    }

    // Get the number of blocks currently in memory
    public int getNumBlocks() {
        return memory.size();
    }

    // Helper method to clear all blocks from memory (for debugging or reset)
    public void clear() {
        memory.clear();
    }
}
