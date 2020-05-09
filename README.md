### CS-321 Project Four: BTree
#### Rachel Singleton, Zachary Sherwood, Jacob Uzabel
#### Professor: Dr. Yeh

#### Layout of BTree:
Our implementation for this project includes the following files
* GeneBankCreateBTree.java --> main program
* BTree.java
* BTreeNode.java
* TreeObject.java
* DataManagement.java
* Cache.java
* GeneBankSearch.java --> main program

GeneBankCreateBTree.java creates the BTree from a .gbk file.  
The binary data file that nodes are written to and read from is created by the BTree constructor.  
GeneBankSearch.java searches a specified binary data file for a specific sequence.  

To run either of the driver classes, use the following arguments:
* `java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]`
* `java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]`
  
Cache size and debug level are optional arguments for both driver classes. 

#### Layout of the BTree on the disk:
We chose to design our BTree like the following:
The only metadata that was written to the binary file was the location of the current root node (an integer taking up 4 bytes). After that, we had nodes being written end to end with all the metadata being written first using a byte buffer and the TreeObjects being written last. The maximum amount of space a node would take up if it was full was 32t+20 bytes which is what we allocated everytime a new node was created. When we wrote things to the file, we chose to add everything into an array and then write the whole array versus writing each part separately. 


#### Issues we ran into:
In terms of team issues, this was very difficult to do remotely as it was harder to get ahold of everyone. One of us moved back home in the middle and another one of us got extremely ill and was unable to contribute anything. 

In terms of design issues, it wasn't until it was too late that we realized our implementation of a BTreeNode with an ArrayList of TreeObjects would not work. We spent close to 20 hours during the last few days of finals week trying to get it to work and sadly it was to no avail. There is a pesky index out of bounds error that we could not fix. To fix it, we would have had to rewrite the entire program and unfortunately, we had no more time. Due to this, we were not able to successfully implement: any cache functionality, dump file creation of a specified sequence and frequency, or any search capability from geneBankSearch. 

Other design issues we ran into was allowing methods to access the RandomAccessFile when they weren't in the class with it, making sure that we were writing the node to the correct place and reading all of the parts back in, using the ArrayList with indexing, and splitting a node that had a parent. Our process had been to create the GeneBankCreateBTree class so that we could get the correct binary strings to pass into our program. To do that, we created a tester class and through that, we were able to successfully validate the correct converted sequences while ignoring any superfluos data or characters. During that time, we also implemented BTree, BTreeNode, and TreeObject. Lastly, we tried to create the DataManagement class that would read and write things to a node. Looking back on it, we probably should have done this in conjunction with the other implementations because then they would have been able to work more fluidly together and there would have been less bugs. 

#### Cache Size Observations:
No Cache implementation was created. 

#### Math to figure out the optimal degree for our BTree:
* metadata for 1 BTreeNode = 17 bytes
* 1 parent pointer = 4 bytes
* array of child pointers = (2t)(4) = 8t bytes
* array of TreeObjects = (2t-1)(12) = 24t-12 bytes

17 + 4 + 8t + 24t - 12 <= 4096  
32t + 9 <= 4096  
t <= 127.71  
Therefore, our optimal degree is t = 127

