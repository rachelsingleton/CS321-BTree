import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class BTree<T> {
    private BTreeNode root;
    private int treeDegree;
    private int seqLen;
    private int isRoot;
    private File btree;
    private File btreeMetaData;
    private String binaryFile;
    private int numNodes;
    int currentNodeLoc = 0;
    private RandomAccessFile btreeRA;
    

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

        createBTree();
    }

    /*
    Creates the first (empty) root node
    Only needs to be called once for each BTree since the other methods will add nodes
    Called in the constructor
     */
    public BTreeNode createBTree() {
        root = new BTreeNode();
        root.setLocation(0);
        root.setRoot(0);
        root.setLeaf();
        writeNode(root);
        this.numNodes = 1;
        return root;
    }

    /*
    Calculates the offset for a new node that is created
    Does not write to the file or add a new node. All it does is the math
     */
    public int allocateSpace(int numberOfNodes) {
        int loc = numberOfNodes * ((32*treeDegree) + 9);
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

    /*
    Inserts the subsequence of type long into the BTree
    Calls upon other methods in this class - this just checks to see if we are full or not-full
    Inserting the sequence versus object to help with logic. We create the tree object after we try to insert.
     */
    public void insertKey(Long data) {
    	TreeObject newObject = new TreeObject(data);
        BTreeNode r = getRoot(); //Needs to return a BTreeNode, not an int
        if(r.getNumKeys() == (2*treeDegree)+1) {
            BTreeNode s = new BTreeNode();
            s.setLocation(allocateSpace(numNodes));
            numNodes++;
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
            if(k.getString() == x.getKey(i)) {
                System.out.println("The object you are trying to insert is a duplicate.");
                k.incrementFreq();
            } else {
                x.setObject(i,k);
                x.setKeys(x.numKeys()+1);
                writeNode(x);
            }
        } else {
            while((i >= 1) && (key < x.getKey(i-1))) {
                i--;
            }
            i++;
            BTreeNode child = x.getChild(i);
            if(child.numKeys() == (2*treeDegree)-1) {
                splitChildNode(x,i-1);
                if(key > x.getKey(i-1)) {
                    i++;
                }
            }
            insertNonFull(x.getChild(i),k);
        }
    }

    /*
    Splits a child node at a specified index when full
    Passing in child number, not actual index of where the child exists in the array
     */
    private void splitChildNode(BTreeNode x, int i) {
        BTreeNode z = new BTreeNode();
        z.setLocation(allocateSpace(numNodes));
        z.setParent(x);
        numNodes++;
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
        writeNode(y);
        writeNode(z);
        writeNode(x);
    }

    public void writeNode(BTreeNode node) {
        currentNodeLoc = node.getLocation();
        try {
            btreeRA.seek(currentNodeLoc);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BTreeNode getRoot() {
        return null;
    }

    public BTreeNode readNode() {

        return null;
    }

//     //DumpFile Method 
//    // Ex. test3.gbk.btree.dump.6
//     public void createTreeDump(int key) throws IOException {
//         PrintWriter pw = null;
//         try {
//             pw = new PrintWriter("dump");
//         } catch (FileNotFoundException e) {
//             System.out.println("createTreeDump - Error in dump file creation.");
//             e.printStackTrace();
//         }
//         traverseTreeDump(pw, root, key);
//         pw.close();
//     }
   
   
//    public void traverseTreeDump(PrintWriter pw, BTreeNode rootNode, int sequenceLength) throws IOException {
//         BTreeNode node = rootNode;
//         int i = 0;
//         for ( i = 0; i < node.getNumKeys(); i++) {

//             if(node.leaf() == false) {
//                 int childPointer = node.getChild(i);
//             }

//         }
//     }
// }

