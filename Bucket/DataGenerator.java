package Bucket;

import java.util.*;

public class DataGenerator {

    private static final int NUM_TUPLES_S = 5000;
    private static final int TUPLES_PER_BLOCK = 8;

    public static void generateRelationS(VirtualDisk disk) {
        Random random = new Random();
        List<Tuple> block = new ArrayList<>();
        
        for (int i = 0; i < NUM_TUPLES_S; i++) {
            int b = 10000 + random.nextInt(40001); // Random B between 10000 and 50000
            String c = "C" + b;                    // Some dummy C value
            Tuple tuple = new Tuple(c, b);         // Using c as attr1, b as attr2
            
            block.add(tuple);

            // If the block is full, write it to disk
            if (block.size() == TUPLES_PER_BLOCK) {
                disk.writeBlock("S", new ArrayList<>(block));
                block.clear();
            }
        }

        // Write remaining tuples (if any)
        if (!block.isEmpty()) {
            disk.writeBlock("S", block);
        }

        System.out.println("Relation S(B, C) with 5000 tuples has been generated and written to disk.");
    }
}
