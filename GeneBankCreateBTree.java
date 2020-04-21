
public class GeneBankCreateBTree {

	public static void main(String[] args) {
		System.out.println("test");
		
//usage is:java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]
		try {
			if (args.length < 4 || args.length  > 6) {
				usage();
		}
			//Todo: logic dealing with cache
			if (!args[0].equals(1) || !args[0].equals(0)) {
				usage();
			}
			
			//Todo: logic dealing with degree
			int degree = Integer.parseInt(args[1]);
			if ( degree < 0 || degree > 31 ) {
				usage();
			}
			
			if (degree == 0) {
				System.out.println("the ideal degree formula");
			}
			
			
			
			

	}

	public static void usage() {
		System.out.println(
				"Usage is: java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length>[<cache size>] [<debug level>]");
	}

}
