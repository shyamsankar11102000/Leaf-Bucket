```
Leaf&Bucket/
│
├── src/                    # Contains B+ Tree implementation files
│   ├── BPlusTree.java
│   ├── BPlusNode.java
│   ├── BPlusLeafNode.java
│   └── BPlusInternalNode.java
│   └── Main.java           # Entry point to run B+ Tree experiments
│
├── Bucket/                # Contains Hash Join simulation
│   ├── Tuple.java
│   ├── VirtualDisk.java
│   ├── VirtualMemory.java
│   ├── DataGenerator.java
│   ├── HashJoin.java
│   └── ExperimentRunner.java  # Entry point to run hash join experiments
│
└── README.md              # This file
```

1. To create classes corresponding to the B+ trees implementation, open a terminal within the Leaf&Bucket directory and run:

```javac src/*.java -d out```

2. This will populate classes within an "out" folder. Now run:

```java -cp out src.Main``` 

3. This should produce results for the first part of the project. (Runs a, b and outputs c1 to c4)

4. To create classes corresponding to the Hash join simulation, move to the directory of Bucket. And run the following command to generate the classes:

```javac *.java```

5. This should create classes within the same file. Now run the ExperimentRunner:

```java ExperimentRunner ```
