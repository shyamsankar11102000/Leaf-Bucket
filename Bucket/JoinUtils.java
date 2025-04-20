package Bucket;

public class JoinUtils {
    public static final int BLOCK_SIZE = 8;         // Tuples per block
    public static final int MEMORY_BLOCKS = 15;     // Total memory blocks
    public static final int NUM_PARTITIONS = MEMORY_BLOCKS - 1;

    public static int hashFunction(int bValue) {
        return bValue % NUM_PARTITIONS;
    }
}
