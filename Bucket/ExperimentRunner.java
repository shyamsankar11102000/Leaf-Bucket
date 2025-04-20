package Bucket;

import java.util.*;

public class ExperimentRunner {

    private static final int TUPLES_PER_BLOCK = 8;

    public static void main(String[] args) {
        VirtualDisk disk = new VirtualDisk();
        VirtualMemory memory = new VirtualMemory(); // 15 blocks in memory
        DataGenerator.generateRelationS(disk); // Part 1: Generate relation S

        // --- 5.1 Generate R with B-values from S ---
        DataGenerator.generateRelationR_FromS(disk, "R1", 1000);
        HashJoin joiner = new HashJoin(disk, memory);
        List<Tuple> joinResult1 = joiner.performHashJoin("R1", "S");
        System.out.println("Experiment 5.1 - Join result size: " + joinResult1.size());

        // Pick 20 random B-values from the result and print matching tuples
        List<Tuple> sample1 = pickRandomBValuesAndPrint(joinResult1, 20);
        System.out.println("Sampled 20 B-values and their joined tuples (5.1):");
        sample1.forEach(System.out::println);

        System.out.println("\n---\n");

        // --- 5.2 Generate R with B-values between 20,000 and 30,000 ---
        int numTuples = 1200;
        DataGenerator.generateRelationR_FromRange(disk, "R", numTuples, 20000, 30000); // Part 5.2
        List<Tuple> joinResult2 = joiner.performHashJoin("R2", "S");
        System.out.println("Experiment 5.2 - Join result size: " + joinResult2.size());

        System.out.println("Joined tuples (5.2):");
        for (Tuple t : joinResult2) {
            System.out.println(t);
        }
    }

    // Helper method: pick 20 random B-values from the join result and return matching tuples
    private static List<Tuple> pickRandomBValuesAndPrint(List<Tuple> joinResult, int count) {
        Set<Integer> seenBs = new HashSet<>();
        List<Integer> bValues = new ArrayList<>();
        Random random = new Random();
    
        // Collect unique B-values
        for (Tuple t : joinResult) {
            Integer b = t.getAttr2(); // Get the B-value
            if (b != null && seenBs.add(b)) { // Add unique B-values to seenBs and bValues
                bValues.add(b);
            }
        }
    
        // Shuffle the B-values and pick a random selection of 'count' B-values
        Collections.shuffle(bValues);
        List<Integer> selected = bValues.subList(0, Math.min(count, bValues.size()));
    
        // Filter the joinResult based on the selected B-values
        List<Tuple> result = new ArrayList<>();
        for (Tuple t : joinResult) {
            Integer b = t.getAttr2(); // Get the B-value
            if (selected.contains(b)) { // If B-value is in the selected list, add it to result
                result.add(t);
            }
        }
    
        // Return the filtered list of tuples
        return result;
    }
    
}

