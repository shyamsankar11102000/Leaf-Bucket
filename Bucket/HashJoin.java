package Bucket;
import java.util.*;
public class HashJoin {
    private final VirtualDisk disk;
    private final VirtualMemory memory;

    public HashJoin(VirtualDisk disk, VirtualMemory memory) {
        this.disk = disk;
        this.memory = memory;
    }

    public List<Tuple> performHashJoin(String relationR, String relationS) {
        Map<Integer, List<List<Tuple>>> rPartitions = partitionRelation(relationR, true);
        Map<Integer, List<List<Tuple>>> sPartitions = partitionRelation(relationS, false);

        List<Tuple> joinResult = new ArrayList<>();

        for (int i = 0; i < JoinUtils.NUM_PARTITIONS; i++) {
            List<Tuple> rTuples = flatten(rPartitions.get(i));  
            Map<Integer, List<Tuple>> hashTable = new HashMap<>();
            for (Tuple t : rTuples) {
                hashTable.computeIfAbsent(t.attr2, k -> new ArrayList<>()).add(t);
            }

            List<List<Tuple>> sBlocks = sPartitions.get(i);
            for (List<Tuple> block : sBlocks) {
                memory.loadBlock(block); 
                for (Tuple s : block) {
                    List<Tuple> matches = hashTable.getOrDefault(s.attr2, Collections.emptyList());
                    for (Tuple r : matches) {
                        joinResult.add(new Tuple(r.attr1, r.attr2, s.attr3)); 
                    }
                }
            }
        }

        return joinResult;
    }

    private Map<Integer, List<List<Tuple>>> partitionRelation(String relName, boolean isR) {
        List<List<Tuple>> blocks = disk.getRelationBlocks(relName);
        Map<Integer, List<List<Tuple>>> partitions = new HashMap<>();
        for (int i = 0; i < JoinUtils.NUM_PARTITIONS; i++) {
            partitions.put(i, new ArrayList<>());
        }

        for (List<Tuple> block : blocks) {
            for (Tuple t : block) {
                int partition = JoinUtils.hashFunction(t.attr2);
                List<List<Tuple>> partitionBlocks = partitions.get(partition);

                
                if (partitionBlocks.isEmpty() || partitionBlocks.get(partitionBlocks.size() - 1).size() >= JoinUtils.BLOCK_SIZE) {
                    partitionBlocks.add(new ArrayList<>());
                }
                partitionBlocks.get(partitionBlocks.size() - 1).add(t);
            }
        }

        return partitions;
    }

    private List<Tuple> flatten(List<List<Tuple>> blocks) {
        List<Tuple> result = new ArrayList<>();
        for (List<Tuple> block : blocks) {
            result.addAll(block);
        }
        return result;
    }
}
