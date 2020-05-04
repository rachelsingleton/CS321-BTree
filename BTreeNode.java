import java.util.ArrayList;

public class BTreeNode {
    private int root;
    private boolean isLeaf;
    private int numKeys;
    private int numChildren;
    private ArrayList<TreeObject> node;
    private int parentloc;
    private ArrayList<Integer> children;
    private int location; // Node's location in binary file

    /*
    Constructor
    Needs to create an empty node and allocate the space somehow
     */
    public BTreeNode() {
        node = new ArrayList<TreeObject>();
        parentloc = 0; //TODO need method to get the parent
        children = new ArrayList<Integer>();
        numKeys = 0;
        root = 1; //means false
        numChildren = 0;
        location = allocateSpace();
        isLeaf = false;
    }

    /*
    Will deal with allocating space in the file depending on t
    Should give the value(int) of the next available spot in the file
    
       /*
     * 
     */
//    public int allocateSpace() {
//    	int loc = (numNodes+1) * ((32*treeDegree)+9);
//    	try {
//    		//seeking and writing to the file
//    	} catch (IOException e){
//    		
//    	}
//    }

    //TODO: not done yet - needs to work with DataManagement I think
    public int allocateSpace() {
        return 0;
    }

    /*
    Returns the list of child pointers for the node
     */
    public ArrayList getChildren() {
        return children;
    }

    /*
    Returns a specified child from the node (returns the actual node, not location in file)
    Put in child number you want but since we are indexing, we have to subtract 1
    Ex: Child 1 is at index 0 in the list
     */
    public BTreeNode getChild(int index) {
        int val = children.get(index-1);
        DataManagement filewriter = new DataManagement();
        return filewriter.readNode(val);
    }

    /*
    Sets the specified child to the location of the specified node
    Ex: want to set child 2 to be node r so setChild(2,r)
    Always increases the number of children since add method does the shifting
    first line: gets the int location of the node in the file since the array stores pointers, not nodes
    Does index conversion in the method
     */
    public void setChild(int index, BTreeNode r) {
        int loc = getLocation(r);
        children.add(index-1,loc);
        numChildren++;
    }

    /*
    Sets the parent of a node to the location of the specified node
     */
    public void setParent(BTreeNode r) {
        int loc = getLocation(r);
        parentloc = loc;
    }

    /*
    Returns the location of the parent node
     */
    public int getParent() {
        return parentloc;
    }

    //TODO should return the location (y offset) from the file - not done yet
    public int getLocation(BTreeNode node) {
        return 0;
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
    Returns the number of keys in the node
     */
    public int numKeys() {
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
    public long getKey(int i) {
        TreeObject temp = node.get(i);
        long substring = temp.getString();
        return substring;
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
        node.add(i,object);
        numKeys++;
    }
}
