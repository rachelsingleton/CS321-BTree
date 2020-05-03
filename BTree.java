import java.io.File;
import java.io.IOException;

public class BTree<T> {
    private BTreeNode root;
    private int treeDegree;
    private int seqLen;
    private int isRoot;
    private File btree;
    private String binaryFile;
    private DataManagement fileWriter;
    

    /* Constructor
    Creates a BTree with only one node
    Set properties of that node to make it an empty root node
     */
    public BTree(int degree, int sequenceLength, String gbkFileName) {

        fileWriter = new DataManagement();
		this.treeDegree = degree;
		this.seqLen = sequenceLength;
		
        try {
            btree = new File(gbkFileName + ".btree.data." + seqLen + "." + treeDegree);
            if (btree.createNewFile()) {
                System.out.println("The file " + btree + "was created successfully.");
            } else {
                //TODO do we need to worry about overwriting a file if we run the simulation again?
            }
        } catch (IOException e) {
            System.out.println("The file could not be created.");
            e.printStackTrace();
        }

		this.binaryFile = btree.getName();

        createBTree();
    }

    /*
    Creates the first (empty) root node
    Only needs to be called once for each BTree since the other methods will add nodes
    Called in the constructor
     */
    public BTreeNode createBTree() {
        root = new BTreeNode();
        root.setRoot(0);
        root.setLeaf();
        fileWriter.writeNode(root);
        return root;
    }

    /*
    Returns the degree of the BTree
     */
    public int getDegree() {
        return treeDegree;
    }

    /*
    Returns the sequence length for this BTree
     */
    public int getK() {
        return seqLen;
    }

    /*
    Returns the name of the file we are writing to
     */
    public String getFile() {
        return binaryFile;
    }

    /*
    Inserts an the subsequence of type long into the BTree
    Calls upon other methods in this class - this just checks to see if we are full or not-full
    Inserting the sequence versus object to help with logic. We create the tree object after we try to insert.
     */
    public void insertKey(Long data) {
    	TreeObject newObject = new TreeObject(data);
        BTreeNode r = fileWriter.getRoot(); //Needs to return a BTreeNode, not an int
        if(r.numKeys() == (2*treeDegree)+1) {
            BTreeNode s = new BTreeNode();
            s.setRoot(0); // New root
            s.setChild(1,r); //Sets the root to be the new child (at index 0)
            r.setParent(s); //Sets the new root to be the parent of the old root
            s.setLeaf(); //Sets the leaf status of the new node
            splitChildNode(s, 1); //Splitting child 1 of the new root node
            insertNonFull(s, newObject);
        } else {
            insertNonFull(r,newObject);
        }

    }

    /*
    Inserts a key into a node when the node is not full and is a leaf node
    Likely that the key will not be inserted in node s
     */
    private void insertNonFull(BTreeNode x, TreeObject k) {
        int i = x.numKeys(); //total number of keys
        long key = k.getString();
        if(x.leaf()) {
            while((i >= 1) && (key < x.getKey(i-1))) { //getting substring from last object (index is one less)
                x.setObject(i,x.getObject(i-1));
                i--;
            }
            //TODO: logic goes here for dealing with duplicates
            x.setObject(i,k);
            x.setKeys(x.numKeys()+1);
            fileWriter.writeNode(x);
        } else {
            while((i >= 1) && (key < x.getKey(i-1))) {
                i--;
            }
            i++;
            fileWriter.readNode(x.getLocation(x.getChild(i)));
            if((x.getChild(i-1)).numKeys() == (2*treeDegree)-1) {
                splitChildNode(x,i-1);
                if(key > x.getKey(i-1)) {
                    i++;
                }
            }
            insertNonFull(x.getChild(i-1),k);
        }
    }

    /*
    Splits a child node at a specified index when full
    Passing in child number, not actual index of where the child exists in the array
     */
    private void splitChildNode(BTreeNode x, int i) {
        BTreeNode z = new BTreeNode();
        BTreeNode y = x.getChild(i); //logic in getChild(i) grabs the correct index
        if(y.leaf()) {
            z.setLeaf();
        }
        z.setKeys(treeDegree - 1);
        for(int j=1; j <= (treeDegree - 1);j++) {
            z.setObject(j-1,y.getObject(j+treeDegree-1));
        }
        if(!y.leaf()) {
            for(int j=1; j <= treeDegree; j++) {
                z.setChild(j,y.getChild(j+treeDegree));
            }
        }
        y.setKeys(treeDegree - 1);
        for(int j=x.numKeys()+1; j>i+1;j--) {
            x.setChild(j+1,x.getChild(j));
        }
        x.setChild(i+1,z);
        for(int j=x.numKeys();j>i;j--) {
            x.setObject(j,x.getObject(j-1));
        }
        x.setObject(i-1,y.getObject(treeDegree-1));
        x.setKeys(x.numKeys()+1);
        fileWriter.writeNode(y);
        fileWriter.writeNode(z);
        fileWriter.writeNode(x);
    }
    
    //TODO:Zach needs to do this
//    private toString( ) {
//    	
//    }
//    
}

