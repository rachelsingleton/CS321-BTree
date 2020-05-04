public class DataManagement {

    /*
    Constructor
     */
    //TODO: not done
    public DataManagement() {

    }

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
    
    
    
    
    // test
    
    /*
     * every 32t+9 is the max amount of room a node would need 
     * 
	Physically writes the byte buffer array to the file
	The byte buffer array contains all information about one specific node
	Location that is written to is the byte where space was allocated for the node
	//TODO: where should the space/location be allocated?
	//TODO: file is created in BTree constructor that we are supposed to write to
     */
    public void writeNode(BTreeNode node) {
    	//byte[] bytes = write(node); --> gives us the actual array
    	byte[] bytes = null;
    	//RandomAccess file stuff will write bytes to the actual file
    }

    /*
    Returns the current root
    Binary file should be storing this location/node as metadata
     */
    //TODO: not done
    public BTreeNode getRoot() {
        return null;
    }

    /*
    Pass in the specified location and it returns the node
    May have to do some converting if we want to grab a specific node but don't have location - ever happen?
    Node doesn't exist until we grab it from it's location which is why we pass in an int instead of a node
     */
    //TODO: not done
    public BTreeNode readNode(int val) {

        return null;
    }
    
    /*
     * Turns the BTreeNode into a byte buffer array
     * Will write all the information into an array (byte[]) for easier access to file
     */
    //TODO: not done
    public void write() {
    	// write(BTreeNode) - how method is used
    	// writeBoolean(), writeInt(), etc... --> maybe?? We may be able to avoid these since that's why we are using
        // a byte buffer array
        // will need to change void to the correct return type
    }
}
