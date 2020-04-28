import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GeneBankCreateBTree {

	public static void main(String[] args) {
		System.out.println("test");
		File file = null;
		int sequenceLength = 0;
		boolean cache = false;
		int cacheSize = 0;
		int debug = 0;

//usage is:java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]
		try {
			if (args.length < 4 || args.length > 6) {
				usage();
			}
			// Todo: logic dealing with cache
			if (!args[0].equals(1) || !args[0].equals(0)) {
				usage();
			}
			if (args[0].equals(1)) {
				cache = true;
			}

			// Todo: logic dealing with degree
			int degree = Integer.parseInt(args[1]);
			if (degree < 0) {
				usage();
			}
			
			// Todo:Math for ideal degree
			// TODO: check to make sure it isn't bigger than ideal degree
			if (degree == 0) {
				System.out.println("the ideal degree formula");
			}

			file = new File(args[2]);
			if (!file.exists() || !file.isFile()) {
				usage();
			}
			sequenceLength = Integer.parseInt(args[3]);
			if (sequenceLength < 1 || sequenceLength > 31) {
				usage();
			}

			if (args.length == 5) {
				if (cache) {
					cacheSize = Integer.parseInt(args[4]);
					if (cacheSize < 1) {
						usage();
					}
				} else {
					debug = Integer.parseInt(args[4]);
					if (debug != 0 || debug != 1) {
						usage();
					}
				}
			} else if (args.length == 6) {
				cacheSize = Integer.parseInt(args[4]);
				if (cacheSize < 1) {
					usage();
				}
				debug = Integer.parseInt(args[5]);
				if (debug != 0 || debug != 1) {
					usage();
				}
			}
			createTree(cache, degree, file, sequenceLength, cacheSize, debug);
		} catch (Exception e) {
			e.printStackTrace();
			usage();
			System.exit(1);

		}
	}

	public static void usage() {
		System.out.println(
				"Usage: java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length>[<cache size>] [<debug level>]");
	}

	public static void createTree(boolean cache, int degree, File file, int sequenceLength, int cacheSize, int debug) {
		try {
			Scanner scanFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
		
		
	}

}
