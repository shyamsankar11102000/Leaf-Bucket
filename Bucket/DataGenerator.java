package Bucket;

import java.util.*;

public class DataGenerator {

    private static final int NUM_TUPLES_S = 5000;
    private static final int TUPLES_PER_BLOCK = 8;

    // PART 1: Generate S(B, C)
    public static void generateRelationS(VirtualDisk disk) {
        Random random = new Random();
        List<Tuple> block = new ArrayList<>();

        for (int i = 0; i < NUM_TUPLES_S; i++) {
            int b = 10000 + random.nextInt(40001); // B between 10000 and 50000
            String c = "C" + b;
            Tuple tuple = new Tuple(c, b); // c = attr1, b = attr2

            block.add(tuple);

            if (block.size() == TUPLES_PER_BLOCK) {
                disk.writeBlock("S", new ArrayList<>(block));
                block.clear();
            }
        }

        if (!block.isEmpty()) {
            disk.writeBlock("S", block);
        }

        System.out.println("Relation S(B, C) generated and written to disk.");
    }

    // PART 5.1: Generate R(A, B) using B-values from S
    public static void generateRelationR_FromS(VirtualDisk disk, String relationName, int numTuples) {
        List<Tuple> sTuples = disk.getAllTuples("S");
        Random random = new Random();
        List<Tuple> block = new ArrayList<>();

        for (int i = 0; i < numTuples; i++) {
            Tuple s = sTuples.get(random.nextInt(sTuples.size()));
            int b = s.getAttr2(); // Get B from S
            String a = "A" + random.nextInt(100000); // Random A
            Tuple r = new Tuple(a, b); // (A, B)

            block.add(r);

            if (block.size() == TUPLES_PER_BLOCK) {
                disk.writeBlock(relationName, new ArrayList<>(block));
                block.clear();
            }
        }

        if (!block.isEmpty()) {
            disk.writeBlock(relationName, block);
        }

        System.out.println("Relation " + relationName + " generated from S with " + numTuples + " tuples.");
    }

    // PART 5.2: Generate R(A, B) with B from a range (not necessarily in S)
    public static void generateRelationR_FromRange(VirtualDisk disk, String relationName, int numTuples, int minB, int maxB) {
        Random random = new Random();
        List<Tuple> block = new ArrayList<>();

        for (int i = 0; i < numTuples; i++) {
            int b = minB + random.nextInt(maxB - minB + 1); // B in [minB, maxB]
            String a = "A" + random.nextInt(100000); // Random A
            Tuple r = new Tuple(a, b);

            block.add(r);

            if (block.size() == TUPLES_PER_BLOCK) {
                disk.writeBlock(relationName, new ArrayList<>(block));
                block.clear();
            }
        }

        if (!block.isEmpty()) {
            disk.writeBlock(relationName, block);
        }

        System.out.println("Relation " + relationName + " generated with random B-values in range.");
    }

    // Helper: Pick random B-values from relation
    public static Set<Integer> pickRandomBsFromRelation(VirtualDisk disk, String relationName, int count) {
        List<Tuple> allTuples = disk.getAllTuples(relationName);
        Set<Integer> picked = new HashSet<>();
        Random rand = new Random();

        while (picked.size() < count && allTuples.size() > 0) {
            int b = allTuples.get(rand.nextInt(allTuples.size())).getAttr2();
            picked.add(b);
        }

        return picked;
    }
}
