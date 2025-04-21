package Bucket;

import java.util.*;

public class VirtualMemory {
    
    private Queue<List<Tuple>> memory;
    private final int MEMORY_CAPACITY = 100;

    public VirtualMemory() {
        memory = new LinkedList<>();
    }

    
    public List<Tuple> getBlock(int blockIndex) {
        if (blockIndex >= 0 && blockIndex < memory.size()) {
            return new ArrayList<>(memory).get(blockIndex);
        }
        return null;
    }

    
    public void loadBlock(List<Tuple> block) {
        if (memory.size() >= MEMORY_CAPACITY) {
            memory.poll(); 
        }
        memory.offer(block);
    }

    
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

    
    public void printMemoryState() {
        int i = 0;
        for (List<Tuple> block : memory) {
            System.out.println("Block " + (i++) + ": " + block);
        }
    }
}
