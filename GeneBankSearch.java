import java.io.File;
import java.util.Arrays;
import java.lang.NumberFormatException;

public class GeneBankSearch {

    //TODO: do file parsing, binary conversion, and call searchBTree() in this class
    // Will need to pass in the root of the tree to call this method
    // The root location can be gotten by the getRoot() method in DataManagement or by reading in the
    // first integer from the binary file --> this will just give a location and then you will have to
    // do other logic to actually get the node (the way you do it depends on if you want a DataManagement object)
    public static void main(String[] args) {
        File btreeFile = null;
        File queryFile = null;
        int sequenceLength = 0;
        int querySeqLen = 0;
        int btreeSeqLen = 0;
        boolean cache = false;
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

            // Grabs the query file
            queryFile = new File(args[2]);
            if (!queryFile.exists() || !queryFile.isFile()) {
                usage();
            }
            queryFileName = queryFile.getName();
            querySeqLen = Integer.parseInt(queryFileName.substring(queryFileName.lastIndexOf('y') + 1));

            // Checking to make sure we are using the correct files
            if(btreeSeqLen != querySeqLen) {
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
                    if (debug != 0 || debug != 1) {
                        usage();
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
        } catch (Exception e) {
            e.printStackTrace();
            usage();
            System.exit(1);

        }
    }

    /*
    Prints out how to use the program to the console
     */
    public static void usage() {
        System.out.println(
                "Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
    }
}

