package Bucket;
import java.util.*;
public class VirtualDisk {
    
    private Map<String, List<List<Tuple>>> disk;

    public VirtualDisk() {
        disk = new HashMap<>();
    }

   
    public void writeBlock(String relationName, List<Tuple> block) {
        
        if (!disk.containsKey(relationName)) {
            disk.put(relationName, new ArrayList<>());
        }

        
        disk.get(relationName).add(block);
    }

    
    public List<Tuple> readBlock(String relationName, int blockIndex) {
       
        if (!disk.containsKey(relationName) || blockIndex >= disk.get(relationName).size()) {
            return null;
        }

        
        return disk.get(relationName).get(blockIndex);
    }

    
    public int getNumBlocks(String relationName) {
        
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

    
    public boolean blockExists(String relationName, int blockIndex) {
        return disk.containsKey(relationName) && blockIndex < disk.get(relationName).size();
    }
}

