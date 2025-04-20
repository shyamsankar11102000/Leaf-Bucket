package Bucket;

public class Tuple {
    private String attr1; // Can be A, B, or C depending on the context
    private int attr2;    // Usually B (common join attribute)
    private String attr3; // Can be C (when used in join results)

    // Constructor for 2-attribute tuple (R or S)
    public Tuple(String attr1, int attr2) {
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = null;
    }

    // Constructor for 3-attribute tuple (Join result)
    public Tuple(String attr1, int attr2, String attr3) {
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
    }

    public String getAttr1() {
        return attr1;
    }

    public int getAttr2() {
        return attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public boolean hasAttr3() {
        return attr3 != null;
    }

    @Override
    public String toString() {
        if (attr3 != null) {
            return "(" + attr1 + ", " + attr2 + ", " + attr3 + ")";
        }
        return "(" + attr1 + ", " + attr2 + ")";
    }
}
