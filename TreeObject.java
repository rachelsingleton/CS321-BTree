public class TreeObject {

	// Instance Variables
	private long key;
	private int frequency;

	// Constructor
	/*
	 * Takes in the key as a substring of type long (should be binary by this point)
	 * Frequency always starts at 1
	 */
	public TreeObject(long substring) {
		key = substring;
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
		return key;
	}

	/*
	 * Printing string
	 * 
	 */
	public String toString() {

		String data = Long.toString(key, 2);

		if (data.length() % 2 != 0 && data.charAt(0) == '0') {
			data = data.replaceFirst("0", "A");
		} else if (data.length() % 2 != 0 && data.charAt(0) == '1') {
			data = data.replaceFirst("1", "C");
		}

		for (int i = 0; i < data.length() - 1; i++) {
			if (data.charAt(i) == '1' && data.charAt(i + 1) == '1') {
				data = data.replaceFirst("11", "T");
			} else if (data.charAt(i) == '1' && data.charAt(i + 1) == '0') {
				data = data.replaceFirst("10", "G");
			} else if (data.charAt(i) == '0' && data.charAt(i + 1) == '1') {
				data = data.replaceFirst("01", "C");
			} else if (data.charAt(i) == '0' && data.charAt(i + 1) == '0') {
				data = data.replaceFirst("00", "A");
			}
		}
		return data;
	}
}
