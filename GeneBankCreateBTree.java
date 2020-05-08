import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GeneBankCreateBTree {

	public static void main(String[] args) {
		File file = null;
		int sequenceLength = 0;
		boolean cache = false;
		int cacheSize = 0;
		int debug = 0;

//usage is:java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]
		try {
			if (args.length < 4 || args.length > 6) {
				System.out.println("Did not use the right number of arguments.");
				usage();
			}
			// Todo: logic dealing with cache
			int cacheValue = Integer.parseInt(args[0]);
			if (cacheValue != 0 && cacheValue != 1) {
				System.out.println("Did not give a valid option for the cache.");
				usage();
			}
			if (args[0].equals(1)) {
				cache = true;
			}

			// Todo: logic dealing with degree
			int degree = Integer.parseInt(args[1]);
			if (degree < 0 || degree > 127) {
				System.out.println("Did not give a valid degree.");
				usage();
			}
			
			// Todo:Math for ideal degree
			if (degree == 0) {
				degree = 127;
			}

			file = new File(args[2]);
			if (!file.exists() || !file.isFile()) {
				System.out.println("The gbk file doesn't exist.");
				usage();
			}
			sequenceLength = Integer.parseInt(args[3]);
			if (sequenceLength < 1 || sequenceLength > 31) {
				System.out.println("Did not give a valid sequence length.");
				usage();
			}

			if (args.length == 5) {
				if (cache) {
					cacheSize = Integer.parseInt(args[4]);
					if (cacheSize < 1) {
						System.out.println("Did not give a valid cache size.");
						usage();
					}
				} else {
					debug = Integer.parseInt(args[4]);
					if (debug != 0 || debug != 1) {
						System.out.println("Did not give a valid debug argument.");
						usage();
					}
				}
			} else if (args.length == 6) {
				cacheSize = Integer.parseInt(args[4]);
				if (cacheSize < 1) {
					System.out.println("Did not give a valid cache size.");
					usage();
				}
				debug = Integer.parseInt(args[5]);
				if (debug != 0 || debug != 1) {
					System.out.println("Did not give a valid debug argument.");
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
				"Usage: java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]");
	}

	public static void createTree(boolean cache, int degree, File file, int sequenceLength, int cacheSize, int debug) {
		try {
					Scanner scanFile = new Scanner(file);
					Scanner fileScan = null;
					//int z = 0;
					try {
						fileScan = new Scanner(file);
					} catch (FileNotFoundException e1) {
						System.err.println("ERROR");
					}
					boolean read = false;

					BTree<Long> tree = null;
					tree = new BTree<Long>(degree ,sequenceLength, file.getName());
					
					
					StringBuilder str = new StringBuilder();
					while (fileScan.hasNextLine()) {
						String line = fileScan.nextLine();
						line = line.toUpperCase();	
						if(read) {
							Scanner lineScan = new Scanner(line);
							if(lineScan.hasNext() && lineScan.next().equals("//")) {
								read = false;

									String currentSection = str.toString();
									currentSection = currentSection.replace("N", "");

//								System.out.println(str.length() + " Section Length w/ n" );
//								System.out.println(currentSection.length() + " Section Length w/o n");
//								System.out.println();
								
								for (int j = 0; j < currentSection.length() - sequenceLength + 1; j++) {
									String dna = currentSection.substring(j, j + sequenceLength);
									
										dna = dna.replace("A", "00");
										dna = dna.replace("T", "11");
										dna = dna.replace("C", "01");
										dna = dna.replace("G", "10");
										
										//z++;
										//System.out.println(dna + " " + z);
										
										long genesBinary = Long.parseLong(dna,2);
										tree.insertKey(genesBinary);

								}
								str = new StringBuilder();
							} else {
								while(lineScan.hasNext()) {
									str.append(lineScan.next());
								}
							}
							
							lineScan.close();
						} else {
							Scanner lineScan = new Scanner(line);
							if(lineScan.hasNext() && lineScan.next().equals("ORIGIN")) {
								read = true;
							}
							lineScan.close();
						}
					}
					fileScan.close();
				

				} catch (FileNotFoundException e) {
					System.out.println("ERROR");
					e.printStackTrace();
				}
				
				
			}

	}


