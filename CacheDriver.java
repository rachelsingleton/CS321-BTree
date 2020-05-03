import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.*;

public class CacheDriver {
    public static void main(String[] args) {
    	// Instance Variables
    	double HR1 = 0;
    	double HR2 = 0;
    	double HR = 0;
    	int NH1 = 0;
    	int NH2 = 0;
    	int NH = 0;
    	int NR1 = 0;
    	int NR2 = 0;
    	int NR = 0;
    	
    	// Using a try-catch statement for my exceptions
    	try {
    		// Check to see if are using a one-level or two-level cache
        	if(Integer.parseInt(args[0]) == 1) {
        		// Test to see if we have the right number of arguments
        		if(args.length != 3) {
        			System.out.println("You have the wrong number of arguments.");
        			System.out.println("Use 'Test 1 <cache size> <input textfile>'");
        		} else {
        			// Test to make sure cache size is positive 
        			if (Integer.parseInt(args[1]) <= 0) {
        				System.out.println("Your cache size is not valid.");
        			} else {
        				// Creating our file!
        				File file = new File(args[2]);
        				int cache_size = Integer.parseInt(args[1]);
        				
        				// If we have the file...
        				if(file.exists() && file.isFile() && file.canRead()) {
        					// Create the cache
        					Cache<String> cache1 = new Cache<String>(cache_size);
        					System.out.println("First level cache with " + cache_size + " entries has been created");
        					Scanner fileScan = new Scanner(file);
        					
        					// Scanning and Tokenization
        					while(fileScan.hasNextLine()) {
        						String line = fileScan.nextLine();
        						StringTokenizer st = new StringTokenizer(line);
        						
        						while(st.hasMoreTokens()) {
        							String str = st.nextToken();
        							NR++;
        							if(cache1.Contains(str)) {
        								NH++;
        								cache1.removeObject(str);
        							} else {
        								cache1.addObject(str);
        							}
        						}
        					}
        					fileScan.close();
        					HR = (double)NH/NR;
        					System.out.println(". . . . . . . . . . . . . . . . . . . . .");
        			        System.out.println("The number of global references: " + NR);
        			        System.out.println("The number of global cache hits: " + NH);
        			        System.out.println("The global hit ratio: " + HR);
        				}
        			}
        		}
        		
        	} else if (Integer.parseInt(args[0]) == 2) {
        		// Checking to make sure we have the right number of arguments
        		if(args.length != 4) {
        			System.out.println("You have the wrong number or arguments.");
        			System.out.println("Use 'Test 2 <cache 1 size> <cache 2 size> <input textfile>'");
        		} else {
        			// Check to see if our cache sizes are positive and cache 2 is bigger than cache 1
        			if (Integer.parseInt(args[1]) <= 0 || Integer.parseInt(args[2]) <= 0) {
        				System.out.println("Your cache sizes are not valid.");
        			} else if(Integer.parseInt(args[2]) < Integer.parseInt(args[1])) {
        				System.out.println("Cache 2 needs to be bigger than Cache 1. Try again.");
        			} else {
        				// Creating our file!
        				File file = new File(args[3]);
        				int cache1_size = Integer.parseInt(args[1]);
        				int cache2_size = Integer.parseInt(args[2]);
        				
        				// If we have the file...
        				if(file.exists() && file.isFile() && file.canRead()) {
        					// Create the cache
        					Cache<String> cache1 = new Cache<String>(cache1_size);
        					Cache<String> cache2 = new Cache<String>(cache2_size);
        					System.out.println("First level cache with " + cache1_size + " entries has been created");
        					System.out.println("Second level cache with " + cache2_size + " entries has been created");
        					Scanner fileScan = new Scanner(file);
        					
        					// Scanning and Tokenization
        					while(fileScan.hasNextLine()) {
        						String line = fileScan.nextLine();
        						StringTokenizer st = new StringTokenizer(line);
        						
        						while(st.hasMoreTokens()) {
        							String str = st.nextToken();
        							NR1++;
        							if(cache1.Contains(str)) {
        								NH1++;
        								cache1.removeObject(str);
        								cache2.removeObject(str);
        							} else {
        								NR2++;
        								if(cache2.Contains(str)) {
        									NH2++;
        									cache2.removeObject(str);
        									cache1.addObject(str);
        								} else {
        									cache1.addObject(str);
        									cache2.addObject(str);
        								}
        							}
        						}
        					}
        					fileScan.close();
        					HR1 = (double)NH1/NR1;
        					HR2 = (double)NH2/NR2;
        					NH = NH1 + NH2;
        					HR = (double)NH/NR1;
        			        System.out.println(". . . . . . . . . . . . . . . . . . . . .");
        			        System.out.println("The number of global references: " + NR1);
        			        System.out.println("The number of global cache hits: " + NH);
        			        System.out.println("The global hit ratio: " + HR);
        			        System.out.println();
        			        System.out.println("The number of 1st-level references: " + NR1);
        			        System.out.println("The number of 1st-level cache hits: " + NH1);
        			        System.out.println("The 1st-level cache hit ratio: " + HR1);
        			        System.out.println();
        			        System.out.println("The number of 2nd-level references: " + NR2);
        			        System.out.println("The number of 2nd-level cache hits: " + NH2);
        			        System.out.println("The 2nd-level cache hit ratio: " + HR2);
        				}
        			}
        		}
        		
        	} else {
        		System.out.println("You are trying to use a Test that doesn't exist. Please "
        				+ "use '1' or '2'");
        	}
    	} 
    	catch(NumberFormatException e) {
    		System.out.println("Your cache size is not a valid number.");
    	} 
    	catch(FileNotFoundException e) {
    		System.out.println("The file you were looking for was not found.");
    	}
    }   
}