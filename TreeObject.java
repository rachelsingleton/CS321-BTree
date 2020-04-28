public class TreeObject {

    //Instance Variables
    private long key;
    private int frequency;


    // Constructor
    /*
    Takes in the key as a substring of type long (should be binary by this point)
    Frequency always starts at 0
     */
    public TreeObject(long substring)  {
        key = substring;
        frequency = 0;
    }

    /*
    Increments the frequency by one
     */
    public void incrementFreq() {
        frequency++;
    }

    /*
    Returns the frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /*
    Returns the key (aka gene substring)
     */
    public long getKey() {
        return key;
    }
}
