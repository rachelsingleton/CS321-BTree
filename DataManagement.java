import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class DataManagement {

    int currentNodeLoc;
    RandomAccessFile treeFile;
    BTreeNode readNode;
    int arrayLength;

    /*
    Constructor
     */
    public DataManagement(RandomAccessFile file, int degree) {
        currentNodeLoc = 0;
        treeFile = file;
        readNode = new BTreeNode(degree);
        arrayLength = 0;
    }

    
    /*
    Every 32t+9 is the max amount of room a node would need where t is the tree degree
	Physically writes the byte buffer array to the file
	The byte buffer array contains all information about one specific node
	Location that is written to is the byte where space was allocated for the node
     */
    public void writeNode(BTreeNode node) {
            currentNodeLoc = node.getLocation();
            try {
                byte[] bytes = arrayFiller(node);
                arrayLength = bytes.length;
                System.out.println(arrayLength);
                treeFile.seek(currentNodeLoc);
                treeFile.write(bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    /*
    Returns the current root
    Binary file should be storing this location/node as metadata as the first 4 ints
     */
    public BTreeNode getRoot() {
        BTreeNode root = null;
        try {
            treeFile.seek(0);
            int loc = treeFile.readInt();
            System.out.println(loc);
            root = readNode(loc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    /*
    Pass in the specified location and it returns the node
    May have to do some converting if we want to grab a specific node but don't have location - ever happen?
    Node doesn't exist until we grab it from it's location which is why we pass in an int instead of a node
     */
    public BTreeNode readNode(int location) {
        try {
            byte[] bytes = new byte[arrayLength];
            treeFile.seek(location);
            treeFile.read(bytes);
            ByteBuffer bb = ByteBuffer.allocate(arrayLength);
            bb.put(bytes);
            bb.rewind();
            System.out.println(bb);
            readNode.setRoot(bb.getInt());
            readNode.setLeafOverload(bb.getInt());
            readNode.setKeys(bb.getInt());
            int numC = bb.getInt();
            readNode.setNumChildren(numC);
            readNode.setParentLoc(bb.getInt());
            int j = 0;
            while (j < numC) {
                readNode.setChildOverload(j, bb.getInt());
                j++;
            }
            readNode.setLocation(bb.getInt());
            readNode.setDegree(bb.getInt());
            int i = 0;
            while (i < numC-1) {
                TreeObject ob = new TreeObject(bb.getLong());
                ob.setFrequency(bb.getInt());
                readNode.setObject(i, ob);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readNode;
    }


    /*
    Creates the array of the data in a node to write it into a binary file
     */
    public byte[] arrayFiller(BTreeNode node) {
        ByteBuffer bb = ByteBuffer.allocate(node.getNodeSize());
        bb.putInt(node.getRootInt());
        boolean isLeafTemp = node.leaf();
        if(isLeafTemp) {
            bb.putInt(0);
        } else {
            bb.putInt(1);
        }
        bb.putInt(node.numKeys());
        bb.putInt(node.getNumChildren());
        bb.putInt(node.getParent());
        ArrayList<Integer> childrenTemp = node.getChildren();
        for (int i = 0; i < childrenTemp.size();i++) {
            bb.putInt(childrenTemp.get(i));
        }
        bb.putInt(node.getLocation());
        bb.putInt(node.getDegree());
        ArrayList<TreeObject> nodeTemp = node.getNode();
        for (int i = 0; i < nodeTemp.size();i++) {
            bb.putLong(nodeTemp.get(i).getSequence());
            bb.putInt(nodeTemp.get(i).getFrequency());
        }
        System.out.println(bb);
        return bb.array();
    }

}
