import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BTree<T> {
    private BTreeNode root;
    private int rootLoc;
    private int treeDegree;
    private int seqLen;
    private File btree;
    private File btreeMetaData;
    private String binaryFile;
    private int numNodes;
    int currentNodeLoc = 0;
    private RandomAccessFile btreeRA;
    private DataManagement filewriter;
    

    /* Constructor
    Creates a BTree with only one node
    Set properties of that node to make it an empty root node
    Calls createBTree
    Creates the binary file and the metadata file
     */
    public BTree(int degree, int sequenceLength, String gbkFileName) {

		this.treeDegree = degree;
		this.seqLen = sequenceLength;
		
		//Creates a file for storing the metadata of the BTree class
        try {
            btreeMetaData = new File(gbkFileName + ".btree.metadata." + seqLen + "." + treeDegree + ".");
            if (btreeMetaData.createNewFile()) {
                System.out.println("The file " + btreeMetaData + " was created successfully.");
            } else if (btreeMetaData.exists()){
                btreeMetaData.delete();
                btreeMetaData.createNewFile();
            } else {
                System.out.println("The file " + btreeMetaData + " could not be created or deleted. Something is wrong.");
            }
        } catch (IOException e) {
            System.out.println("The metadata file could not be created.");
            e.printStackTrace();
        }


        // Creates the binary data file that all the node information is stored in
        try {
            btree = new File(gbkFileName + ".btree.data." + seqLen + "." + treeDegree + ".");
            if (btree.createNewFile()) {
                System.out.println("The file " + btree + " was created successfully.");
            } else if (btree.exists()){
                btree.delete();
                btree.createNewFile();
            } else {
                System.out.println("The file " + btree + " could not be created or deleted. Something is wrong.");
            }
        } catch (IOException e) {
            System.out.println("The binary file could not be created.");
            e.printStackTrace();
        }

		this.binaryFile = btree.getName();
        try {
            btreeRA = new RandomAccessFile(binaryFile,"rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
        filewriter = new DataManagement(btreeRA,treeDegree);
        createBTree();
    }

    /*
    Creates the first (empty) root node
    Only needs to be called once for each BTree since the other methods will add nodes
    Called in the constructor
     */
    public BTreeNode createBTree() {
        root = new BTreeNode(treeDegree);
        root.setLocation(4);
        root.setRoot(0);
        rootLoc = 4;
        root.setLeaf();
        try {
            // The first root location should be at index 0 in the binary file
            btreeRA.seek(0);
            btreeRA.writeInt(rootLoc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        filewriter.writeNode(root);
        this.numNodes = 1;
        return root;
    }

    /*
    Calculates the offset for a new node that is created
    Does not write to the file or add a new node. All it does is the math
     */
    public int allocateSpace(int numberOfNodes) {
        int loc = numberOfNodes * ((32*treeDegree) + 17);
        return loc;
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

    public File getMetaDataFile() {
        return btreeMetaData;
    }

    public int getRootLoc() {
        return rootLoc;
    }

    /*
    Inserts an the subsequence of type long into the BTree
    Calls upon other methods in this class - this just checks to see if we are full or not-full
    Inserting the sequence versus object to help with logic. We create the tree object after we try to insert.
     */
    public void insertKey(Long data) {
        System.out.println("Inserting...");
    	TreeObject newObject = new TreeObject(data);
        BTreeNode r = filewriter.getRoot(); //Needs to return a BTreeNode, not an int
        if(r.numKeys() == (2*treeDegree)+1) {
            BTreeNode s = new BTreeNode(treeDegree);
            numNodes++;
            s.setLocation(allocateSpace(numNodes));
            s.setRoot(0); // New root
            rootLoc = s.getLocation();
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
        int i = x.numKeys() - 1; //total number of keys
        long key = k.getSequence();
        if(x.leaf()) {
            while((i >= 0) && (key < x.getKey(i))) { //getting substring from last object (index is one less)
                x.setObject(i,x.getObject(i));
                i--;
            }
            if(i >= 0 && k.getSequence() == x.getKey(i)) {
                System.out.println("The object you are trying to insert is a duplicate.");
                k.incrementFreq();
            } else {
                x.setObject(i+1,k);
                x.setKeys(x.numKeys()+1);
                System.out.println("Going to write node to file...");
                filewriter.writeNode(x);
                System.out.println("Done inserting...");
            }
        } else {
            while((i >= 0) && (key < x.getKey(i-1))) {
                i--;
            }
            if(i >= 0 && k.getSequence() == x.getKey(i)) {
                System.out.println("The object you are trying to insert is a duplicate.");
                k.incrementFreq();
            } else {
	            i++;
	            BTreeNode child = x.getChild(i,btreeRA);
	            if(child.numKeys() == (2*treeDegree)-1) {
	                splitChildNode(x,i);
	                if(key > x.getKey(i)) {
	                    i++;
	                }
	            }
	            insertNonFull(x.getChild(i,btreeRA),k);
            }
        }
    }

    /*
    Splits a child node at a specified index when full
    Passing in child number, not actual index of where the child exists in the array
     */
    private void splitChildNode(BTreeNode x, int i) {
        BTreeNode z = new BTreeNode(treeDegree);
        numNodes++;
        z.setLocation(allocateSpace(numNodes));
        z.setParent(x);
        BTreeNode y = x.getChild(i,btreeRA); //logic in getChild(i) grabs the correct index
        if(y.leaf()) {
            z.setLeaf();
        }
        z.setKeys(treeDegree - 1);
        for(int j=0; j < (treeDegree);j++) {
            z.setObject(j,y.getObject(j+treeDegree));
        }
        if(!y.leaf()) {
            for(int j=0; j < treeDegree; j++) {
                z.setChild(j,y.getChild(j+treeDegree,btreeRA));
            }
        }
        y.setKeys(treeDegree - 1);
        for(int j=x.numKeys(); j > i;j--) {
            x.setChild(j+1,x.getChild(j,btreeRA));
        }
        x.setChild(i+1,z);
        for(int j=x.numKeys()-1;j > i-1;j--) {
            x.setObject(j+1,x.getObject(j));
        }
        x.setObject(i,y.getObject(treeDegree-1));
        x.setKeys(x.numKeys()+1);
        filewriter.writeNode(y);
        filewriter.writeNode(z);
        filewriter.writeNode(x);
    }

    /*
    Searches the given BTree file for a given key from a query file
     */
    //TODO: What should this return? - logic is done except for return statement stuff
    public void searchBTree(Long data,BTreeNode treeRoot,RandomAccessFile btreeFile) {
        int i = 1;
        while(i <= treeRoot.numKeys() && (data > treeRoot.getKey(i-1))) {
            i++;
        }
        if(i <= treeRoot.numKeys() && (data == treeRoot.getKey(i-1))) {
            //TODO: What do we want to return here?
        } else if(treeRoot.leaf()) {
            //TODO: What do we want to return if it hasn't been found?
        } else {
            BTreeNode child = treeRoot.getChild(i,btreeFile);
            searchBTree(data,child,btreeFile);
        }
    }


    //DumpFile Method 
    //Ex. test3.gbk.btree.dump.6
//    public void createDumpFile() {
//     try {
//		FileWriter dumpFile = new FileWriter("TestDumpFile");
//	} catch (IOException e) {
//		System.out.println("Error");
//		e.printStackTrace();
//	}
//    }
//    
//    public void printToDumpFile(FileWriter dumpFile, BTreeNode root, int sequenceLength) {
//    	BTreeNode CurrentNode = root;
//    	
//    	
//    }
//    
}

