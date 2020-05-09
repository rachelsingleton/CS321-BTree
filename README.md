### CS-321 Project Four: BTree
#### Rachel Singleton, Zachary Sherwood, Jacob Uzabel, Michael Smith
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


#### Issues we ran into:
This project came at a difficult time for everyone in our group. Not only did we have one group member (Rachel Singleton) who was forced
to move back to Seattle, Washington late-semester. We also had one member (Michael Smith) who contracted some illness and was not able to contribute anything to the project.
We were not able to successfully implement: any cache functionality, dump file creation of a specified sequence and frequency, and any search
capability from geneBankSearch. Many problems arose from miscommunications, work (and loss of) and a general inability to access resources that would have
been available to us if we had not been forced to access our classes remotely (tutoring centers, in-person LA meetings, etc.).

We started creating our geneBankCreateBTree class with an express purpose of converting the sequences to their correct binary representation. We made a tester to test the output
via the command line and were able to successfully validate the correct the converted sequences ignoring any superflous data or characters. Once that was complete, my group agreed
that we should send that next step would be to pass that converted long value into the Btree insertKey() class. 

#### Cache Size Observations:

#### Math to figure out the optimal degree for our BTree:
* metadata for 1 BTreeNode = 17 bytes
* 1 parent pointer = 4 bytes
* array of child pointers = (2t)(4) = 8t bytes
* array of TreeObjects = (2t-1)(12) = 24t-12 bytes

17 + 4 + 8t + 24t - 12 <= 4096  
32t + 9 <= 4096  
t <= 127.71  
Therefore, our optimal degree is t = 127

