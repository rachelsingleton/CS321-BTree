import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TestBinaryConverter {

	public static void main(String[] args) {

		if (args.length == 2) {

			File file = null;
			int sequenceLength = 0;

			sequenceLength = Integer.parseInt(args[0]);
			file = new File(args[1]);

			createBinaryBasic(sequenceLength, file);
		}

	}

	public static void createBinaryBasic(int sequenceLength, File file) {
		Scanner fileScan = null;
		int z = 0;
		try {
			fileScan = new Scanner(file);
		} catch (FileNotFoundException e1) {
			System.err.println("ERROR");
		}
		boolean read = false;

		// BTree<Long> tree =
		// tree = new Tree(t ,sequenceLength, gbkfileName);
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

					System.out.println(str.length() + " Section Length w/ n" );
					
					System.out.println(currentSection.length() + " Section Length w/o n");
					System.out.println();
					for (int j = 0; j < currentSection.length() - sequenceLength + 1; j++) {
						String dna = currentSection.substring(j, j + sequenceLength);
						
							dna = dna.replace("A", "00");
							dna = dna.replace("T", "11");
							dna = dna.replace("C", "01");
							dna = dna.replace("G", "10");
							
							//z++;
							//System.out.println(dna + " " + z);
							
							//long genesBinary = parseLong(dna,2);
							//TreeObject tree = new TreeObject(genesBinary);
							//btree.insertKey(tree);

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
	}
	

		
	}


