import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class BTreeNode {

    // Instance Variables
    private int root;
    private boolean isLeaf;
    private int numKeys;
    private int numChildren;
    private int parentloc;
    private ArrayList<Integer> children;
    private int location; // Node's location in binary file
    private int degree;
    private ArrayList<TreeObject> node;

    /*
    Constructor
    Needs to create an empty node and allocate the space somehow
     */
    public BTreeNode(int degree) {
        node = new ArrayList<TreeObject>();
        parentloc = -2;
        children = new ArrayList<Integer>();
        numKeys = 0;
        root = 1; //means false
        numChildren = 0;
        isLeaf = false;
        location = -2;
        this.degree = degree;
    }

    /*
    Returns the list of child pointers for the node
     */
    public ArrayList getChildren() {
        return children;
    }

    public void removeObject(int index) {
        node.remove(index);
    }

    /*
    Returns a specified child from the node (returns the actual node, not location in file)
     */
    public BTreeNode getChild(int index, RandomAccessFile file) {
        int val = children.get(index);
        DataManagement filewriter = new DataManagement(file,degree);
        return filewriter.readNode(val);
    }

    /*
    Always increases the number of children since add method does the shifting
    first line: gets the int location of the node in the file since the array stores pointers, not nodes
     */
    public void setChild(int index, BTreeNode r) {
        int loc = r.getLocation();
        if(index == children.size()){
            children.add(index, loc);
            numChildren++;
        } else {
            children.set(index, loc);
        }

    }

    /*
    Used for reading files
     */
    public void setChildOverload(int index, int value) {
        children.add(index,value);
    }

    /*
    Sets the parent of a node to the location of the specified node
     */
    public void setParent(BTreeNode r) {
        parentloc = r.getLocation();
    }

    /*
    Returns the location of the parent node
     */
    public int getParent() {
        return parentloc;
    }

    /*
    Sets the location of the parent for a node
     */
    public void setParentLoc(int value) {
        parentloc = value;
    }

    /*
    Returns the byte offset of a node in the file
     */
    public int getLocation() {
        return location;
    }

    /*
    Sets the byte offset of the node in the file
     */
    public void setLocation(int location) {
        this.location = location;
    }

    /*
    Returns whether the node is the root or not
     */
    public boolean isRoot() {
        if(root == 0){
            return true;
        } else {
            return false;
        }
    }

    /*
    Returns whether the node is a leaf or not
     */
    public boolean leaf() {
        return isLeaf;
    }

    /*
    Sets the node to be a leaf node or not based on number of children
     */
    public boolean setLeaf() {
        if(numChildren == 0) {
            isLeaf = true;
        } else {
            isLeaf = false;
        }
        return isLeaf;
    }

    /*
    Used for reading files
     */
    public void setLeafOverload(int val){
        if(val==0) {
            isLeaf = true;
        } else {
            isLeaf = false;
        }
    }

    /*
    Sets the number of children for a node
     */
    public void setNumChildren(int value) {
        numChildren = value;
    }

    /*
    Returns the number of keys in the node
     */
    public int getNumKeys() {
        return numKeys;
    }

    /*
    Sets the number of keys that are in the node
     */
    public void setKeys(int num) {
        numKeys = num;
    }

    /*
    Sets the node to be a root node or not depending on input
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

    /*
    Returns the specified key from inside a node
     */
    public String getKey(int i,int seqLen) {
        TreeObject temp = node.get(i);
        long substring = temp.getSequence();
        String actual = Long.toBinaryString(substring);
        while(actual.length() < seqLen*2) {
            actual = "0" + actual;
        }
        return actual;
    }

    /*
    Returns the specified TreeObject from the node
    Index has already been converted so no need to subtract
     */
    public TreeObject getObject(int i) {
        return node.get(i);
    }

    /*
    Passing in the index, not the object number
    Ex: setting index 0 to object1 is setObject(0,object1)
    Taking the place of an add method
     */
    public void setObject(int i, TreeObject object) {
        if(i == node.size()){
            node.add(i, object);
            numKeys++;
        } else {
            node.set(i, object);
        }
    }

    /*
    Returns the number of children for a node
     */
    public int getNumChildren() {
        return numChildren;
    }

    /*
    Returns the ArrayList of TreeObjects
     */
    public ArrayList getNode() {
        return node;
    }

    /*
    Used with reading files
     */
    public int getRootInt() {
        return root;
    }

    /*
    Calculates the maximum size of a node based on the degree t
     */
    public int getNodeSize() {
        return ((32*degree) + 20);
    }

    /*
    Returns the degree of a node
     */
    public int getDegree() {
        return degree;
    }

    /*
    Sets the degree of a node - used with reading files
     */
    public void setDegree(int value) {
        degree = value;
    }
}
