public class TreeObject {

	// Instance Variables
	private long keyValue;
	private int frequency;

	// Constructor
	/*
	 * Takes in the key as a substring of type long (should be binary by this point)
	 * Frequency always starts at 1
	 */
	public TreeObject(long substring) {
		keyValue = substring;
		frequency = 1;
	}

	/*
	 * Increments the frequency by one
	 */
	public void incrementFreq() {
		frequency++;
	}

	/*
	 * Returns the frequency
	 */
	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int value) {
		frequency = value;
	}

	/*
	 * Returns the key (aka gene substring)
	 */
	public long getSequence() {
		return keyValue;
	}

	/*
	 * Printing string
	 * 
	 */
	public String toString() {

		String genesBinary = Long.toString(keyValue, 2);

		if (genesBinary.length() % 2 != 0 && genesBinary.charAt(0) == '0') {
			genesBinary = genesBinary.replaceFirst("0", "A");
		} else if (genesBinary.length() % 2 != 0 && genesBinary.charAt(0) == '1') {
			genesBinary = genesBinary.replaceFirst("1", "C");
		}

		for (int i = 0; i < genesBinary.length() - 1; i++) {
			if (genesBinary.charAt(i) == '1' && genesBinary.charAt(i + 1) == '1') {
				genesBinary = genesBinary.replaceFirst("11", "T");
			} else if (genesBinary.charAt(i) == '1' && genesBinary.charAt(i + 1) == '0') {
				genesBinary = genesBinary.replaceFirst("10", "G");
			} else if (genesBinary.charAt(i) == '0' && genesBinary.charAt(i + 1) == '1') {
				genesBinary = genesBinary.replaceFirst("01", "C");
			} else if (genesBinary.charAt(i) == '0' && genesBinary.charAt(i + 1) == '0') {
				genesBinary = genesBinary.replaceFirst("00", "A");
			}
		}
		return genesBinary;
	}
}
