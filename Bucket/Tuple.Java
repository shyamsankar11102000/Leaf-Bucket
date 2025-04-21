package Bucket;
public class Tuple {
    public String attr1; 
    public int attr2;    
    public String attr3; 

    
    public Tuple(String attr1, int attr2) {
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = null;
    }

    
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
