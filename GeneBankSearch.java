import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.NumberFormatException;

public class GeneBankSearch {

	static int degree = 0;
	static DataManagement filewriter = null;

	// TODO: do file parsing, binary conversion, and call searchBTree() in this
	// class
	// Will need to pass in the root of the tree to call this method
	// The root location can be gotten by the getRoot() method in DataManagement or
	// by reading in the
	// first integer from the binary file --> this will just give a location and
	// then you will have to
	// do other logic to actually get the node (the way you do it depends on if you
	// want a DataManagement object)
	public static void main(String[] args) {
		File btreeFile = null;
		File queryFile = null;
		int sequenceLength = 0;
		int querySeqLen = 0;
		int btreeSeqLen = 0;
		boolean cache = false;
		boolean debugFlag = false;
		int cacheSize = 0;
		int debug = 0;
		String[] btreeFileName;
		String queryFileName;

		try {
			// Checks to make sure we have a valid number of arguments
			if (args.length < 3 || args.length > 5) {
				usage();
			}
			// Todo: logic dealing with cache --> what does this mean?
			// Checks to see if we entered a correct value for cache enabling
			if (!args[0].equals(1) || !args[0].equals(0)) {
				usage();
			}

			// Checks to see if we have cache enabled
			if (args[0].equals(1)) {
				cache = true;
			}

			// Grabs the btree file
			btreeFile = new File(args[1]);
			if (!btreeFile.exists() || !btreeFile.isFile()) {
				usage();
			}
			btreeFileName = btreeFile.getName().split("\\.");
			btreeSeqLen = Integer.parseInt(btreeFileName[4]);
			degree = Integer.parseInt(btreeFileName[5]);

			// Grabs the query file
			queryFile = new File(args[2]);
			if (!queryFile.exists() || !queryFile.isFile()) {
				usage();
			}
			queryFileName = queryFile.getName();
			querySeqLen = Integer.parseInt(queryFileName.substring(queryFileName.lastIndexOf('y') + 1));

			// Checking to make sure we are using the correct files
			if (btreeSeqLen != querySeqLen) {
				System.out.println("You are using the wrong query file or btree file. The k's must match.");
			} else {
				sequenceLength = btreeSeqLen;
			}

			if (args.length == 4) {
				// Checks to see if cache is enabled when we have 4 arguments
				if (cache) {
					cacheSize = Integer.parseInt(args[3]);
					if (cacheSize < 1) {
						usage();
					}
				} else {
					// Use debug if cache isn't enabled
					debug = Integer.parseInt(args[3]);
					if (debug != 0) {
						usage();
					} else {
						debugFlag = true;
					}
				}
				// Both optional arguments are enabled
			} else if (args.length == 5) {
				cacheSize = Integer.parseInt(args[3]);
				if (cacheSize < 1) {
					usage();
				}
				debug = Integer.parseInt(args[4]);
				if (debug != 0 || debug != 1) {
					usage();
				}
			}

			RandomAccessFile file = new RandomAccessFile(btreeFile, "r");
			filewriter = new DataManagement(file, degree);
			file.seek(0);
			int root = file.readInt();

			// read through the query file converting strings into Binary

			Scanner scanQueryFile = new Scanner(queryFile);
			String queryLine;
			String line;

			while (scanQueryFile.hasNextLine()) {
				queryLine = scanQueryFile.nextLine();
				line = queryLine.toUpperCase();
				queryLine = queryLine.replaceAll("\\s+", "");

				queryLine = queryLine.replace("A", "00");
				queryLine = queryLine.replace("T", "11");
				queryLine = queryLine.replace("C", "01");
				queryLine = queryLine.replace("G", "10");
				long queryBinary = Long.parseLong(queryLine, 2);

				searchBTree(queryBinary, root, file);
			}

		} catch (Exception e) {
			e.printStackTrace();
			usage();
			System.exit(1);
		}
	}

	/*
	 * Prints out how to use the program to the console
	 */
	public static void usage() {
		System.out.println(
				"Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
	}

	/*
	 * Searches the given BTree file for a given key from a query file
	 */
	// TODO: What should this return? - logic is done except for return statement
	// stuff
	public static void searchBTree(Long data, int root, RandomAccessFile btreeFile) {
		BTreeNode treeRoot = filewriter.readNode(root);
		int i = 1;
		while (i <= treeRoot.numKeys() && (data > treeRoot.getKey(i - 1))) {
			i++;
		}
		if (i <= treeRoot.numKeys() && (data == treeRoot.getKey(i - 1))) {
			System.out.println(treeRoot.getObject(i - 1).toString() + ": " + treeRoot.getObject(i - 1).getFrequency());
		} else if (treeRoot.leaf()) {
			System.out.println(DecodeToString(data) + ": 0");
		} else {
			BTreeNode child = treeRoot.getChild(i, btreeFile);
			searchBTree(data, child.getLocation(), btreeFile);
		}
	}

	public static String DecodeToString(long key) {

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
