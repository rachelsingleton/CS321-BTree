public class BTreeNode {
    private int root;
    private boolean isLeaf;
    private int numKeys;
    private int numChildren;

    /*
    Constructor
    Needs to create an empty node and allocate the space somehow
     */
    public BTreeNode() {
        numKeys = 0;
        root = 1;
        numChildren = 0; // Should be true since we always create the leaf node? have to check this logic
    }

    public boolean isRoot() {
        if(root == 0){
            return true;
        } else {
            return false;
        }

    }

    public boolean leaf() {
        if(isLeaf == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean setLeaf() {
        if(numChildren == 0) {
            isLeaf = true;
        } else {
            isLeaf = false;
        }
        return isLeaf;
    }

    public int numKeys() {
        return numKeys;
    }

    public void setKeys(int num) {
        numKeys = num;
    }

    /*
    0 means it is the root node
    1 means it is not the root node
     */
    public void setRoot(int val) {
        if(val == 0) {
            root = 0;
        } else if (val == 1) {
            root = 1;
        } else {
            System.out.println("The root value was not valid. Use 0 (True) or 1 (False).");
        }
    }
}
