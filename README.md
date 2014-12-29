##JASTR Problem Set by John Tabone
----

I challenged myself by writing these solutions in **Scala**, a language that I've had some yet limited experience with as it was assigned to me in a PL class last year. In hindsight, it may have been wise to stick to a language that I'm a bit more familair with, but in the end, this exercise definitely made me a better Scala programmer. 

Anyway, the code for each of the problems can be found in their respective folders in this repository, which are structured in the Scala/Java scaffolding style. Written answers for the solutions and some code elaboration can be found in this README.  

###Problem 1
===
	Write two functions:
		A) int findMostConsecutivelyRepeatingValue (int[] arr)	A function where the input is an array of integers and the output is the value that	appears the most times consecutively (in a row). Ex: [1, 2, 2, 5, 5, 5, 2, 2] => 5
			B) int findMostFrequentValue (int[] arr)	A function where the input is an array of integers and the output is the value that	appears most frequently. Ex: [1, 2, 2, 5, 5, 5, 2, 2] => 2
			C) What is the Big-O complexity of your solution (in both time and space)? Why?
I traversed an array to find the most consecutively repeating value and did the same, but also used a map to find the mosr frequent value.
The Big-O complexity for **findMostConsecutivelyRepeatingValue** is ***O(n)*** in time and ***O(1)*** in space as we are searching through the entire array and dealing with a constant space (i.e. not creating any new arrays, just traversing the input array).
The Big-O complexity for **findMostFrequentValue** is ***O(n^2) in time*** due to the nested loop (looping through the array of numbers and for each number, looping through the map) and ***O(n) in space*** due to the fact that at any time in my solution, n, the number of unique numbers in the input array, is the maximum amount of space being used (i.e. creating keys and values for each unique integer in the input array).


###Problem 2
===
	A) You are given a large set of log files (k > 100) and each line of the file has a	timestamp and an error message. Each file has (n > 10000) lines. The lines in	each file are sorted by timestamp in ascending order, naturally.
			The API for interacting with this data is the following (pseudo-code):
				struct LogLine {			long timestamp;			String message;		}
					int getTotalNumberOfLogFiles();		int getTotalNumberOfLinesInFile(int k);		List<LogLine> readAllLogLinesFromFile(k);		The goal is to produce a single log file with the combined data from all the log	files, sorted in ascending order, for example by calling an API:
				void appendLogLinesToOutput(List<LogLine> lines);
				B) What is the Big-O complexity of your solution (in both time and space)? Why?
			C) What if the full set of data could not all fit in memory (e.g. k > 1000, n >	1000000)? Given this constraint, is it possible to solve this problem with only the	API you’ve been given? Assuming you could change the interface/implementation of the API, what would you modify to make the solution easier?

So I took some creative license here and modified the spec that was given to me. I used an ArrayBuffer instead of a List as the immutable nature of a Scala List makes using the provided sorting methods a chore. In fact, sorting the List took several minutes while sorting the ArrayBuffer just took a second or so. Also, I provided ten test log files that each have 10,000 lines.

readAllLogLinesFromFile doesn't return an ArrayBuffer of log lines since I declared the ArrayBuffer of LogLines within the Scala object and I'm appending each log line that I read into the final ArrayBuffer in this method. 

To count the files, it takes O(k) in time, which doesn't concern n so we can disregard that. To read and store each log line, it takes O(n) in time. To sort the log lines, it takes O(nlog(n)) in time. To write to the final, sorted log file, it takes O(n) in time. Therefore, the Big-O complexity of my solution is ***O(nlog(n))*** in time since sorting the log lines is the most significant process in terms of time complexity. Furthermore, the Big-O complexity of my solution in space is ***O(n)*** as the size of the list of log lines is directly related to the size of the input.


Given the memory constraint described in part C, I don't believe that this problem can be solved with the API that I've been given. There are several ways that I'd go about fixing this. Assuming that each log file is still sorted by timestamp in ascending order, we can deal with this memory constraint by not loading all the log files into memory at once. Instead, we can read in the first line of each log file and sorting those based on their timestamps. In other words, we would be sorting the log lines as they are being read. Given Scala's access to the Java library, we can also use a priority queue (a heap implementation) that would allow us to use a timestamp as a "score", making sorting through a large amount of files and lines more efficent and less expensive.  


###Problem 3
===
	A) Write a function where the input is a binary search tree of integers and the	output is the Nth-greatest value in the tree. The BST is defined by a root node	and the property: every node on the right subtree has to be larger than the	current node and every node on the left subtree has to be smaller (or equal) than	the current node.			struct Node {			Node* left;			Node* right;			int value;		}
					int findNthGreatestValueInBST(Node* root, int n) {}
				Ex: calling the function on this tree with n = 2 should yield 20, n = 4 should yield 40.
						30 <--root				/ \			   20 40		      /		     10
		     	B) What is the Big-O complexity of your solution (in both time and space)? Why?

Problem 3 caused me the most issues surprisingly. I've created Binary Search trees in C, C#, C++, and Java and none have been as tricky as this. Given that using null values in Scala is a no-no, I decided to use the Option feature in Scala, which is similar to using null in Java for an optional value, but instead doesn't throw NullPointerExceptions. I hadn't used this feature when I worked with Scala last year so I had to do some reading. An Option object is either an instance of the Scala None class or an instance of the Scala Some class. As you can probably tell, None denotes that no value has been assigned while Some denotes that some value has been assigned. 

The Option object proved useful in creating my Tree Class (not sure why I chose Tree over Node, but I did). The value was still an integer, but the left and right trees are initialzed as Option[Tree] = None, meaning that children may or may not exist for the current tree. 

I decided on in-order traversal to create and maintain a list of all the values of the nodes in the example tree I created in my code. After performing the in-order traversal, I find the nth value by accessing the value at n-1 in the list. 

Since I am doing an in-order traversal, I'm visiting every node i.e. n nodes. Therefore, the Big-O complexity in time is ***O(n)***. The Big-O compelxity in space is also ***O(n)*** because the list being created is of size n.