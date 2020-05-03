public class TreeObject {

    //Instance Variables
    private long key;
    private int frequency;


    // Constructor
    /*
    Takes in the key as a substring of type long (should be binary by this point)
    Frequency always starts at 1
     */
    public TreeObject(long substring)  {
        key = substring;
        frequency = 1;
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
    public long getString() {
        return key;
    }

    /*
    TODO: Zach said he would do this!!!!
    Printing string and frequency for dump file
     */
    public String toString() {

        return null;
    }
}
