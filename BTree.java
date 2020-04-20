/* This is a simple Java program.
   FileName : "HelloWorld.java". */
public class BTree
{
    private BTreeNode[] btree;
    private BTreeNode root;

    /* Constructor
    Creates a BTree with only one node
    Set properties of that node to make it an empty root node
     */
    public BTree() {
        btree = new BTreeNode[1];
        root = btree.getNode(); //TODO need methods similar to this from BTreeNode
        root.setRoot() = True; //TODO need methods similar to this from BTreeNode
        root.setLeaf() = True; //TODO need methods similar to this from BTreeNode
        root.numKeys() = 0; //TODO need methods similar to this from BTreeNode

        //TODO need to write the root to disk now

    }

    /*
    Creates the first (empty) root node
    Only needs to be called once for each BTree since the other methods will add nodes
    @param supposed to take in T which is the BTree we are building
     */
    public BTree createBTree() {
        return null;
    }
    //TODO is above method actually needed?
}

