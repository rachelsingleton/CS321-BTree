import java.io.File;
import java.io.IOException;

public class BTree {
    private BTreeNode root;
    private int treeDegree;
    private int seqLen;
    private int isRoot;
    private File btree;
    private DataManagement fileWriter;

    /* Constructor
    Creates a BTree with only one node
    Set properties of that node to make it an empty root node
     */
    public BTree(int degree, int sequenceLength, File file) {
        treeDegree = degree;
        seqLen = sequenceLength;
        try {
            btree = new File(file + ".btree.data." + seqLen + "." + treeDegree);
            if (btree.createNewFile()) {
                System.out.println("The file " + btree + "was created successfully.");
            } else {
                //TODO do we need to worry about overwriting a file if we run the simulation again?
            }
        } catch (IOException e) {
            System.out.println("The file could not be created.");
            e.printStackTrace();
        }
        fileWriter = new DataManagement();
    }

    /*
    Creates the first (empty) root node
    Only needs to be called once for each BTree since the other methods will add nodes
    @param supposed to take in T which is the BTree we are building
     */
    public BTreeNode createBTree() {
        root = new BTreeNode();
        root.setRoot(0); //TODO method needs to be implemented
        root.setLeaf();
        fileWriter.writeNode(root);
        return root;
    }

    public int getDegree() {
        return treeDegree;
    }

    public int getK() {
        return seqLen;
    }

    public File getFile() {
        return btree;
    }

    public void insertKey(TreeObject k) {
        BTreeNode r = fileWriter.getRoot();
        if(r.numKeys() == (2*treeDegree)+1) {
            BTreeNode s = new BTreeNode();
            s.setRoot(0);
            s.setLeaf();
            //TODO need to set s's child to the root here (s.c1 = r)
            splitNode(s, 1);
            insertNonFull(s, k);
        } else {
            insertNonFull(r,k);
        }

    }

    private void insertNonFull(BTreeNode s, TreeObject k) {

    }

    private void splitNode(BTreeNode x, int i) {
        BTreeNode z = new BTreeNode();
        //TODO y=x.c1 where x is the node we are splitting - not quite sure where y comes from?
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
        //TODO the rest of the algorithm but I'm going to bed

    }
}

