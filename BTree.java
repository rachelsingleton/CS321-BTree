import java.io.File;
import java.io.IOException;

public class BTree<T> {
    private BTreeNode root;
    private int treeDegree;
    private int seqLen;
    private int isRoot;
    private File btree;
    private String gbkFileName;
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

		this.gbkFileName = gbkFileName + ".btree.data." + seqLen + "." + treeDegree;

        createBTree();
    }

    /*
    Creates the first (empty) root node
    Only needs to be called once for each BTree since the other methods will add nodes
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
    Returns the file we are writing to
     */
    public File getFile() {
        return btree;
    }

    /*
    Inserts an object k into the BTree
    Calls upon other methods in this class - this just checks to see if we are full or not-full
     */
    public void insertKey(Long data) {
    	TreeObject newObject = new TreeObject(data);
        BTreeNode r = fileWriter.getRoot();
        if(r.numKeys() == (2*treeDegree)+1) {
            BTreeNode s = new BTreeNode();
            s.setRoot(0); // New root
            s.setChild(1,r); //Sets the root to be the new child
            r.setParent(s); //Sets the new root to be the parent of the old root
            s.setLeaf(); //Sets the leaf status of the new node
            splitChildNode(s, 1);
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
        int i = x.numKeys();
        long key = k.getKey();
        if(x.leaf()) {
            while((i >= 1) && (key < x.getKey(i))) {
                x.setObject(i+1,x.getObject(i));
                i--;
            }
            x.setObject(i+1,k);
            x.setKeys(x.numKeys()+1);
            fileWriter.writeNode(x);
        } else {
            while((i >= 1) && (key < x.getKey(i))) {
                i--;
            }
            i++;
            //TODO Disk-Read(x.c_i)
            if((x.getChild(i)).numKeys() == (2*treeDegree)-1) {
                splitChildNode(x,i);
                if(key > x.getKey(i)) {
                    i++;
                }
            }
            insertNonFull(x.getChild(i),k);
        }
    }

    /*
    Splits a child node at a specified index when full
     */
    private void splitChildNode(BTreeNode x, int i) {
        BTreeNode z = new BTreeNode();
        BTreeNode y = x.getChild(i);
        //TODO z.leaf = y.leaf - not sure if this is just setting leaf status or if something more
        z.setKeys(treeDegree - 1);
        for(int j=1; j <= (treeDegree - 1);j++) {
            //TODO z.key_j = y.key_j+1
        }
        if(!y.leaf()) {
            for(int j=1; j <= treeDegree; j++) {
                //TODO z.c_j = y.c_j+1
            }
        }
        y.setKeys(treeDegree - 1);
        for(int j=x.numKeys()+1; j>i+1;j--) {
            //TODO x.c_j+1 = x.c_j
        }
        x.setChild(i+1,z);
        for(int j=x.numKeys();j>i;j--) {
            //TODO x.key_j+1 = x.key_j
        }
        //TODO x.key_i = y.key_t
        x.setKeys(x.numKeys()+1);
        fileWriter.writeNode(y);
        fileWriter.writeNode(z);
        fileWriter.writeNode(x);
    }
    
    //Zach needs to do this
//    private toString( ) {
//    	
//    }
//    
}

