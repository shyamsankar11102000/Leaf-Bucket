package Bucket;

import java.util.*;

public class DataGenerator {
    static final int NUM_TUPLES = 5000;
    static final int BLOCK_SIZE = 8;
    static final int B_MIN = 10000;
    static final int B_MAX = 50000;

    static class Tuple {
        int b;
        String c;

        Tuple(int b, String c) {
            this.b = b;
            this.c = c;
        }

        @Override
        public String toString() {
            return "(" + b + ", " + c + ")";
        }
    }

    static class Block {
        List<Tuple> tuples = new ArrayList<>();

        boolean isFull() {
            return tuples.size() >= BLOCK_SIZE;
        }

        void add(Tuple t) {
            if (!isFull()) {
                tuples.add(t);
            }
        }

        @Override
        public String toString() {
            return tuples.toString();
        }
    }
    public class DiskManager {
        static final int BLOCK_SIZE = 8;
        static final int MEMORY_SIZE = 15;  // 15 blocks
        static List<Block> virtualDisk = new ArrayList<>();
        static List<Block> virtualMemory = new ArrayList<>(MEMORY_SIZE);
        static int diskIOCount = 0;
    
        static class Tuple {
            int b;
            String c;
    
            Tuple(int b, String c) {
                this.b = b;
                this.c = c;
            }
    
            @Override
            public String toString() {
                return "(" + b + ", " + c + ")";
            }
        }
    
        static class Block {
            List<Tuple> tuples = new ArrayList<>();
    
            boolean isFull() {
                return tuples.size() >= BLOCK_SIZE;
            }
    
            void add(Tuple t) {
                if (!isFull()) {
                    tuples.add(t);
                }
            }
    
            @Override
            public String toString() {
                return tuples.toString();
            }
        }
    
        // Read a block from disk to memory
        public static Block readBlock(int blockIndex) {
            if (blockIndex < 0 || blockIndex >= virtualDisk.size()) {
                throw new IndexOutOfBoundsException("Invalid block index");
            }
            diskIOCount++;
            return virtualDisk.get(blockIndex);
        }
    
        // Write a block from memory to disk
        public static void writeBlock(Block block) {
            virtualDisk.add(block);
            diskIOCount++;
        }
    
        public static void clearMemory() {
            virtualMemory.clear();
        }
    
        public static void loadToMemory(List<Integer> blockIndices) {
            clearMemory();
            for (int idx : blockIndices) {
                if (virtualMemory.size() >= MEMORY_SIZE) break;
                virtualMemory.add(readBlock(idx));
            }
        }
    
        public static void printMemory() {
            System.out.println("---- Memory ----");
            for (int i = 0; i < virtualMemory.size(); i++) {
                System.out.println("Block " + i + ": " + virtualMemory.get(i));
            }
        }
    
        public static int getDiskIOCount() {
            return diskIOCount;
        }
    }

    // Virtual Disk
    static List<Block> virtualDisk = new ArrayList<>();

    public static void main(String[] args) {
        generateRelationS();
        System.out.println("Generated relation S with " + virtualDisk.size() + " blocks.");
    }

    public static void generateRelationS() {
        Set<Integer> usedB = new HashSet<>();
        Random rand = new Random();
        Block currentBlock = new Block();

        while (usedB.size() < NUM_TUPLES) {
            int b = B_MIN + rand.nextInt(B_MAX - B_MIN + 1);
            if (usedB.contains(b)) continue;

            String c = "C" + rand.nextInt(1000);  // Example: random string

            Tuple tuple = new Tuple(b, c);
            currentBlock.add(tuple);
            usedB.add(b);

            if (currentBlock.isFull()) {
                virtualDisk.add(currentBlock);
                currentBlock = new Block();
            }
        }

        // Add any remaining tuples in the last block
        if (!currentBlock.tuples.isEmpty()) {
            virtualDisk.add(currentBlock);
        }
    }
}