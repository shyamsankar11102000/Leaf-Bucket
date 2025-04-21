package src;
import java.util.*;

public class DataGenerator {

    public static List<Integer> generateRecords(int numRecords, int lowerBound, int upperBound) {
        if (upperBound - lowerBound + 1 < numRecords) {
            throw new IllegalArgumentException("Range is too small for the number of unique records requested.");
        }

        Set<Integer> uniqueKeys = new HashSet<>();
        Random rand = new Random();

        while (uniqueKeys.size() < numRecords) {
            int key = rand.nextInt(upperBound - lowerBound + 1) + lowerBound;
            uniqueKeys.add(key);
        }

        List<Integer> recordList = new ArrayList<>(uniqueKeys);
        Collections.sort(recordList); 
        return recordList;
    }

    public static void main(String[] args) {
        List<Integer> records = generateRecords(10000, 100000, 200000);
        System.out.println("Generated " + records.size() + " records.");
        System.out.println("Sample: " + records.subList(0, 10));  
    }
}
