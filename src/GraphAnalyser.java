///////////////////////////////////////////////////////////////////////////////
//
// Title:            CS367 Programming Assignment 5 : Twitter
// Files:            BasicGraph.java, Graphnode.java
// Semester:         Spring 2012
//
// Author:           Corbin Schwalm
// Lecturer's Name:  Beck Hasti
// Lab Section:      N/A
//
//
// Pair Partner:     N/A
// CS Login:         N/A
// Lecturer's Name:  N/A
// Lab Section:      N/A
//
//
// Credits:          N/A
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.*;
import java.util.*;

/**
 * GraphAnalyser uses a BasicGraph to represent and process a graph of Twitter 
 * users and the follow connections between users. Nodes in our graph will be 
 * user names and an edge from user1 to user2 will be used to represent the 
 * connection "user2 follows user1".
 * 
 * @author Corbin Schwalm
 */
public class GraphAnalyser {
	
	/** Storage for the users. Users are stored as nodes in this graph. */
	private BasicGraph userList;
	
	/**
	 * Sets up the program for use by instantiating the graph.
	 */
	public GraphAnalyser() {
		
		this.userList = new BasicGraph();
		
	} //end GraphAnalyser
	
	/**
	 * The command line format for the application is:
	 * "java GraphAnalyser fileName" where fileName is the name of the text 
	 * file containing the Twitter user information to be processed.
	 * 
	 * @param args - [0] filename : File
	 */
	public static void main(String[] args) {
		
		/* Storage for the GraphAnalyser Object and BasicGraph access. */
		GraphAnalyser gA = new GraphAnalyser();
		
		/* Storage for the file object. */
		File inputFile;
		
		/* Storage for the input handler. */
		Scanner in = null;
		
		if (args.length != 1) {
			
			System.out.println("Usage: java GraphAnalyser fileName");
			
			System.exit(1);
			
		}
		
		inputFile = new File(args[0]);
			
		try {
			
			in = new Scanner(inputFile);
			
		} catch (IOException ex) {
			
			System.out.println("Error: Cannot access input file");
			
			System.exit(1);
		}
		
		gA.loadFile(in); //Loads the inputFile and populates the BasicGraph.
		
		in.close(); //Close the input stream.
		
		gA.displayStats();
		
		gA.displayMostFollowers();
		
		gA.displayNumNoFollowers();
		
		gA.displayNumFollowNoOne();
		
		gA.displayMostTweets();
		
		gA.displayCyclicPaths();
		
		gA.displayMostUsersReached();
		
	} //end main
	
	/**
	 * Method takes a properly formatted input source and populates
	 * the BasicGraph with usernames.
	 * 
	 * E.g. username:follower1,follower2,...,followerN
	 * 
	 * @param in Source of the usernames to populate the graph.
	 */
	private void loadFile(Scanner in) {
		
		/* Storage for the current entire line. */
		String currentLine;
		
		/* Storage for the array of the split of the currentLine. */
		String[] split;
		
		/* Storage for the main username on each line. */
		String currentUsername;
		
		/* Storage for the String of followers. */
		String followerString;
		
		/* Storage for the list of followers after the main username. */
		String[] currentFollowers;
		
		while (in.hasNextLine()) {
			
			currentLine = in.nextLine();
			
			split = currentLine.split(":");
			
			currentUsername = split[0];
			
			followerString = split[1];
			
			currentFollowers = followerString.split(",");
			
			this.userList.addNode(currentUsername); //Creates the main username.
			
			/* Creates usernames for the followers and makes them followers. */
			for (int i = 0; i < currentFollowers.length; i++) {
				
				this.userList.addNode(currentFollowers[i]);
				
				this.userList.addEdge(currentUsername, currentFollowers[i]);
				
			}
			
		}
		
	} //end loadFile
	
	/**
	 * Method displays statistics about the BasicGraph such as number of users
	 * and the number of followers.
	 */
	private void displayStats() {
		
		/* Storage for the number of followers. */
		int numFollowers = 0;
		
		for (String user : this.userList)	
			numFollowers += this.userList.successors(user).size();
		
		System.out.println("Number of users: " + this.userList.size());
		
		System.out.println("Number of follows links: " + numFollowers);
		
		System.out.println("DFS visit order: " + 
				this.userList.dfs(this.userList.iterator().next()));
		
		System.out.println("BFS visit order: " + 
				this.userList.bfs(this.userList.iterator().next()));
		
	} //end displayStats
	
	/**
	 * Prints which user can reach the most people with a single tweet.
	 */
	private void displayMostFollowers() {
		
		/* Highest number of users reached counter. Can't be negative. */
		int userCount = 0;
		
		/* Storage for the username that can reach the most people. */
		ArrayList<String> mostUser = new ArrayList<String>();
		
		for (String user : this.userList) {
			
			if (this.userList.successors(user).size() == userCount) {
				
				mostUser.add(user);
				
			} else if (this.userList.successors(user).size() > userCount) {
				
				mostUser.clear(); //Empties the current entries.
				
				mostUser.add(user);
				
				userCount = this.userList.successors(user).size();
			}
			
		}
		
		System.out.println("Most users: " + mostUser.toString());
		
	} //end displayMostFollowers
	
	/**
	 * Prints the number of users that have no followers.
	 */
	private void displayNumNoFollowers() {
		
		/* Storage for the users that have no followers. */
		int noFollowers = 0;
		
		for (String user : this.userList) {
			
			if (this.userList.successors(user).size() == 0)
				noFollowers++;
		}
		
		System.out.println("No followers: " + noFollowers);
			
	} //end displayNumNoFollowers
	
	/**
	 * Prints the number of users that follow no one.
	 */
	private void displayNumFollowNoOne() {
		
		/* Storage for the number of users that follow no one. Can't be <0 */
		int numFollowNoOne = 0;
		
		/* Flag to keep track if user follows no one. */
		boolean followNoOne = false;
		
		for (String followUser : this.userList) {
			
			for (String user : this.userList) {
			
				if (this.userList.successors(user).contains(followUser))
					followNoOne = true;			
			}
			
			if (!followNoOne)
				numFollowNoOne++;
			
			followNoOne = false;
			
		}
		
		System.out.println("Don't follow anyone: " + numFollowNoOne);
			
	} //end displayNumFollowNoOne
	
	/**
	 * Prints the username(s) that receives the most tweets.
	 */
	private void displayMostTweets() {
		
		/* Storage for the num of users that will receive tweets Can't be <0 */
		int numPredecessors = 0;
		
		/* Storage for the current running total for the user. */
		int currentNumPredecessors = 0;
		
		/* Storage for the user that will receive the most tweets. */
		ArrayList<String> maxUser = new ArrayList<String>();
		
		for (String preUser : this.userList) {
			
			for (String user : this.userList) {
			
				if (this.userList.successors(user).contains(preUser))
					currentNumPredecessors++;			
			}
			
			if (currentNumPredecessors > numPredecessors) {
				
				maxUser.clear();
				
				maxUser.add(preUser);
				
				numPredecessors = currentNumPredecessors;
				
			} else if (currentNumPredecessors == numPredecessors) {
				
				maxUser.add(preUser);
			}
			
			currentNumPredecessors = 0;
		}
		
		System.out.println("Receive most tweets: " + maxUser);
		
	} //end displayMostTweets
	
	/**
	 * Prints the number then users of the largest cyclic path.
	 */
	private void displayCyclicPaths() {
		
		/* Storage for the user(s) that has the greatest cyclic path. */
		ArrayList<String> cyclicUsers = new ArrayList<String>();
		
		for (String user : this.userList) {

			if (this.userList.cyclicPath(user) != null)
				cyclicUsers.add(user);
		}
		
		System.out.println("Tweet reaches self: " + cyclicUsers.size() + " " + 
				cyclicUsers.toString());
		
	} //end displayGreatestCyclicPath
	
	/**
	 * Prints the number and user who can tweet the most overall users.
	 */
	private void displayMostUsersReached() {
		
		/* Storage for the username(s) that can tweet the most users. */
		ArrayList<String> mostUser = new ArrayList<String>();
		
		/* Storage for visited node. Prevents infinite loops. */
		ArrayList<String> visited = new ArrayList<String>();
		
		/* Storage for the count of the username that can tweet the most. */
		int mostUserCount = 0;
		
		/* Storage for the count of tweets for the current username. */
		int currentUserCount = 0;
		
		for (String user : this.userList) {
			
			visited.clear();
			
			currentUserCount = calcMostUserReached(user, visited);
			
			if (currentUserCount > mostUserCount) {
				
				mostUser.clear();
				
				mostUserCount = currentUserCount;
				
				mostUser.add(user);
				
			} else if (currentUserCount == mostUserCount) {
				
				mostUser.add(user);
	
			}
			
		}
		
		mostUserCount++; //Accounts for recursion solution.

		System.out.println("Most users reached: " + mostUserCount + " " + 
				mostUser.toString());
		
	} //end displayMostUsersReached
	
	/**
	 * Auxiliary method to recursively calculate the user that can reach the 
	 * most other users.
	 * 
	 * @param username The username to perform the count on.
	 * @param usersReached Current running total of the users reached.
	 * @param visited List that stores visited nodes. Prevents loops.
	 * @return The number of users the particular user can reach.
	 */
	private int calcMostUserReached(String username, ArrayList<String> visited) {
		
		/* Running total for the current number of users reached. >0 */
		int usersReached = 0;
		
		visited.add(username);
		
		if (this.userList.successors(username).size() == 0)
			return 0;
		
		for (String successor : this.userList.successors(username)) {
				
			if (!visited.contains(successor)) {
				
				usersReached++;
				
				usersReached += calcMostUserReached(successor, visited);
			}
			
		}
		
		return usersReached;
		
	} //end calcMostUserReached

} //end GraphAnalyser