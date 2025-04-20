package Bucket;
import java.util.*;
public class VirtualDisk {
    // Map to simulate the virtual disk. Each relation has a list of blocks (each block is a list of tuples)
    private Map<String, List<List<Tuple>>> disk;

    // Constructor
    public VirtualDisk() {
        disk = new HashMap<>();
    }

    // Write a block to the virtual disk for a given relation
    public void writeBlock(String relationName, List<Tuple> block) {
        // Initialize relation's block list if it doesn't exist yet
        if (!disk.containsKey(relationName)) {
            disk.put(relationName, new ArrayList<>());
        }

        // Add the new block to the relation's list of blocks
        disk.get(relationName).add(block);
    }

    // Read a block from the virtual disk for a given relation
    public List<Tuple> readBlock(String relationName, int blockIndex) {
        // Check if the relation exists and if the blockIndex is valid
        if (!disk.containsKey(relationName) || blockIndex >= disk.get(relationName).size()) {
            return null;
        }

        // Return the requested block
        return disk.get(relationName).get(blockIndex);
    }

    // Get the number of blocks stored for a given relation
    public int getNumBlocks(String relationName) {
        // Return the number of blocks for the relation, or 0 if the relation does not exist
        if (!disk.containsKey(relationName)) {
            return 0;
        }
        return disk.get(relationName).size();
    }
    public List<List<Tuple>> getRelationBlocks(String relationName) {
        return disk.getOrDefault(relationName, new ArrayList<>());
    }
    public List<Tuple> getAllTuples(String relationName) {
        List<Tuple> allTuples = new ArrayList<>();
        List<List<Tuple>> blocks = disk.getOrDefault(relationName, new ArrayList<>());
    
        for (List<Tuple> block : blocks) {
            allTuples.addAll(block);
        }
    
        return allTuples;
    }

    // Helper method to check if a block exists in the disk (for debugging)
    public boolean blockExists(String relationName, int blockIndex) {
        return disk.containsKey(relationName) && blockIndex < disk.get(relationName).size();
    }
}

