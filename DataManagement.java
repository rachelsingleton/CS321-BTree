public class DataManagement {

    public DataManagement() {

    }

    /*
	Physically writes the byte buffer array to the file
     */
    public void writeNode(BTreeNode node) {
    	//byte[] bytes = write(node);
    	//RandomAccess file stuff will write bytes to the actual file
    }

    /*

     */
    public BTreeNode getRoot() {
        return null;
    }

    /*
    Pass in the specified location and it returns the node
    May have to do some converting if we want to grab a specific node but don't have location
    //TODO talk about this logic
     */
    public BTreeNode readNode(int val) {

        return null;
    }
    
    /*
     * Turns the BTreeNode into a byte buffer array
     */
    public void write() {
    	//write all the stuff to a byte[]
    	// write(BTreeNode)
    	// writeBoolean(), writeInt(), etc...
    }
}
