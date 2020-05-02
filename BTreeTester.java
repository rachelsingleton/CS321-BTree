
public class BTreeTester {

	public BTreeTester() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		BTree<Long> test = new BTree<Long>(2,2,"test");
		
		test.insertKey(convert("AA"));
		test.insertKey(convert("TT"));
//		test.insertKey(convert("TA"));
//		test.insertKey(convert("TC"));
//		test.insertKey(convert("TG"));
//		test.insertKey(convert("CT"));
//		test.insertKey(convert("CA"));
//		test.insertKey(convert("CC"));
//		test.insertKey(convert("CG"));
//		test.insertKey(convert("GT"));
//		test.insertKey(convert("GA"));
//		test.insertKey(convert("GC"));
//		test.insertKey(convert("GG"));
//		test.insertKey(convert("TT"));
//		test.insertKey(convert("TA"));
//		test.insertKey(convert("TC"));
//		test.insertKey(convert("TG"));
//		test.insertKey(convert("CT"));
//		test.insertKey(convert("CA"));
//		test.insertKey(convert("CC"));
//		test.insertKey(convert("CG"));
//		test.insertKey(convert("GT"));
//		test.insertKey(convert("GA"));
//		test.insertKey(convert("GC"));
//		test.insertKey(convert("GG"));
//		test.insertKey(convert("TT"));		
		System.out.println("Insertion\n------------------\n"+test.toString());

	}
	
	private static long convert(String dna) {
		dna = dna.replace("A", "00");
		dna = dna.replace("T", "11");
		dna = dna.replace("C", "01");
		dna = dna.replace("G", "10");
		return Long.parseLong(dna, 2);
	}

}
