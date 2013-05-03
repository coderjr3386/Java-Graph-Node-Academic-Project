///////////////////////////////////////////////////////////////////////////////
//
// Main Class File:  GraphAnalyser.java
// File:             BasicGraph.java
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

import java.util.*;

/**
 * The BasicGraph class represents a directed graph with string labels for nodes.
 * Node labels are assumed to be unique and it is assumed that nodes do not have 
 * edges to themselves (i.e., no self edges).
 * 
 * @author Corbin Schwalm
 */
public class BasicGraph implements Iterable<String> {
	
	/** Storage for the graph nodes of the Basic Graph. */
	private TreeMap<String, Graphnode> graphNodes;
	
	/** Storage for the number of entries in the Basic Graph. Can't be negative. */
	private int size;
	
	/**
	 * Constructs a new empty graph.
	 */
	public BasicGraph() {
		
		this.size = 0;
		
		this.graphNodes = new TreeMap<String, Graphnode>();
		
	} //end constructor
	
	/**
	 * Adds a node with the given label to the graph. If a node with this label
	 * is already in the graph, the graph is unchanged and false is returned.
	 * Otherwise (i.e., if there previously had not been a node with this
	 * label and a new node with this label is added to the graph),
	 * true is returned. 
	 * 
	 * @param label Label of newly added node.
	 * @return False if a node with the given label is already in the graph,
	 * true otherwise.
	 * @throws IllegalArgumentException If label is null.
	 */
	public boolean addNode(String label) {
		
		if (label == null)
			throw new IllegalArgumentException("Label is null");
		
		if (this.graphNodes.containsKey(label))
				return false;
		
		this.graphNodes.put(label, new Graphnode(label));
		
		this.size++;
		
		return true;

	} //end addNode
	
	/**
	 * Return true if and only if a node with the given label is in the graph.
	 * 
	 * @param label Label of node to check.
	 * @return True if there is a node with the given label in the graph.
	 * @throws IllegalArgumentException If label is null.
	 */
	public boolean hasNode(String label) {
		
		if (label == null)
			throw new IllegalArgumentException("Label is null");
		
		if (this.graphNodes.containsKey(label))
			return true;
		
		return false;
		
	} //end hasNode
	
	/**
	 * Return labels of immediate successors of the given node in alphabetical order.
	 * 
	 * @param label Label of the start node.
	 * @return Labels of immediate neighbors of the start node in alphabetical order.
	 * @throws IllegalArgumentException If label is null or if there is no node
	 *  in the graph with the given label.
	 */
	public List<String> successors(String label) {
		
		if (label == null || !this.hasNode(label))
			throw new IllegalArgumentException("Null label or non-node.");
		
		/* Storage for the successors converted to a list of Strings. */
		ArrayList<String> successorList = new ArrayList<String>();
		
		for (Graphnode currentNode : this.graphNodes.get(label).getSuccessors())
			successorList.add(currentNode.getLabel());
		
		return successorList;
		
	} //end successors
	
	/**
	 * Add to the graph the specified directed edge from the node with the label
	 * sourceLabel to the node with the label targetLabel. If the edge is 
	 * already in the graph, the graph is unchanged and false is returned. 
	 * Otherwise (i.e., if edge is not already in the graph), true is returned. 
	 * 
	 * @param sourceLabel Label of source node of the edge.
	 * @param targetLabel Label of target node of the edge.
	 * @return False if the graph already contains the edge, true otherwise.
	 * @throws IllegalArgumentException If either label is null or if there are 
	 * no nodes in the graph with the given labels.
	 */
	public boolean addEdge(String sourceLabel, String targetLabel) {
		
		if (sourceLabel == null || targetLabel == null)
			throw new IllegalArgumentException("One of the labels are null.");
		
		if (!this.graphNodes.containsKey(sourceLabel) || 
				!this.graphNodes.containsKey(targetLabel))
			throw new IllegalArgumentException("Non-existant node.");
		
		/* Storage for the source node. */
		Graphnode sourceNode = this.graphNodes.get(sourceLabel);
		
		/* Storage for the target node. */
		Graphnode targetNode = this.graphNodes.get(targetLabel);
		
		if (sourceNode.getSuccessors().contains(targetNode))
			return false;
		
		/* Adds the target node as a successor of the source node. */
		sourceNode.getSuccessors().add(targetNode);
		
		return true;
		
	} //end addEdge

	/**
	 * Return true if and only if the graph contains an edge from the node with
	 * the label sourceLabel to the node with the label targetLabel.
	 * 
	 * @param sourceLabel Label of source node of the edge.
	 * @param targetLabel Label of target node of the edge.
	 * @return False if the graph already contains the edge, true otherwise.
	 * @throws IllegalArgumentException If either label is null or if there are 
	 * no nodes in the graph with the given labels.
	 */
	public boolean hasEdge(String sourceLabel, String targetLabel) {
		
		if (sourceLabel == null || targetLabel == null)
			throw new IllegalArgumentException("One of the labels are null.");
		
		if (!this.graphNodes.containsKey(sourceLabel) || 
				!this.graphNodes.containsKey(targetLabel))
			throw new IllegalArgumentException("Non-existant node.");
		
		/* Storage for the source node. */
		Graphnode sourceNode = this.graphNodes.get(sourceLabel);
		
		/* Storage for the target node. */
		Graphnode targetNode = this.graphNodes.get(targetLabel);
		
		if (sourceNode.getSuccessors().contains(targetNode))
			return true;
		
		return false;
		
	} //end addEdge
	
	/**
	 * Return a list of node labels in the order they are visited using a 
	 * depth-first search starting at the node with the given label. 
	 * Whenever a node has multiple successors, the successors are visited 
	 * in alphabetical order. 
	 * 
	 * @param startLabel Label of the start node.
	 * @return List of the node labels in DFS order.
	 * @throws IllegalArgumentException If startLabel is null or there is
	 * no node with this label in the graph.
	 */
	public List<String> dfs(String startLabel) {
		
		if (startLabel == null || !this.hasNode(startLabel))
			throw new IllegalArgumentException("Null label or non-node.");
		
		/* Storage for the node to start the search on. */
		Graphnode startNode = this.graphNodes.get(startLabel);
		
		/* Creates the list to store the dfs entries. */
		List<String> graphnodeList = new ArrayList<String>();
		
		this.markNodes(); //Marks all of the nodes unvisited.
		
		this.dfs(startNode, graphnodeList);
		
		return graphnodeList;

	} //end dfs
	
	/**
	 * Auxiliary method that uses recursion to implement a depth-first search.
	 * 
	 * @param n The node to start the dfs on.
	 * @param graphnodeList The list to store the dfs entries.
	 */
	private void dfs(Graphnode n, List<String> graphnodeList) {
		
		n.setVisitFlag(true);
		
		graphnodeList.add(n.getLabel());
		
		for (Graphnode m : n.getSuccessors()) {
			
	        if (!m.isVisited()) 
	            dfs(m, graphnodeList);
	        
		}
	
	} //end dfs
	
	/**
	 * Return a list of node labels in the order they are visited using a 
	 * breadth-first search starting at the node with the given label. Whenever 
	 * a node has multiple successors, the successors are visited in 
	 * alphabetical order.
	 * 
	 * @param startLabel Label of the start node.
	 * @return List of the node labels in BFS order.
	 * @throws IllegalArgumentException If startLabel is null or there is no 
	 * node with this label in the graph.
	 */
	public List<String> bfs(String startLabel) {
		
		if (startLabel == null || !this.hasNode(startLabel))
			throw new IllegalArgumentException("Null label or non-node.");
		
		/* Storage for the node to start the search on. */
		Graphnode startNode = this.graphNodes.get(startLabel);
		
		/* Storage for the current graphnode. */
		Graphnode currentNode;
		
		/* Creates the list to store the bfs entries. */
		List<String> graphnodeList = new ArrayList<String>();
		
		/* Storage for the queue to process the bfs. */
		Queue<Graphnode> queue = new LinkedList<Graphnode>();
		
		this.markNodes(); //Marks all of the nodes unvisited.
		
		startNode.setVisitFlag(true);
		
		graphnodeList.add(startNode.getLabel());
		
		queue.offer(startNode); //Enqueues the current node.
		
		while (!queue.isEmpty()) {
			
			currentNode = queue.poll(); //Dequeues the next node.
			
			for (Graphnode k : currentNode.getSuccessors()) {
				
				if (!k.isVisited()) {
					
					k.setVisitFlag(true);
					
					graphnodeList.add(k.getLabel());
					
					queue.offer(k);
				} //end if
				
			} //end forEach
		} //end while
		
		return graphnodeList;
		
	} //end bfs
	
	/**
	 * Find a cyclic path that starts and ends at the given node. Whenever a 
	 * node has multiple successors, the successors are visited in alphabetical 
	 * order. Returns the complete list of node labels along the path, with the 
	 * start node label appearing first and last. The path, if it is found, 
	 * is non-trivial, i.e., it contains at least one edge. If there is not a 
	 * cyclic path that starts and ends at the given node, null is returned.
	 * 
	 * @param startLabel Label of the start node.
	 * @return Sequence of nodes along the cyclic path, starting and ending at 
	 * the start node, or null if there is no such path.
	 * @throws IllegalArgumentException If startLabel is null or there is no 
	 * node with this label in the graph.
	 */
	public List<String> cyclicPath(String startLabel) {
		
		if (startLabel == null || !this.hasNode(startLabel))
			throw new IllegalArgumentException("Null label or non-node.");
		
		/* Storage for the node to start the search on. */
		Graphnode startNode = this.graphNodes.get(startLabel);
		
		/* Storage for the list containing the cyclic path. */
		LinkedList<String> graphnodeList = new LinkedList<String>();
		
		this.markNodes(); //Marks the nodes unvisited.
		
		if (!this.cyclicPath(startNode, graphnodeList))
			return null; //Return null if no cyclic path.
		
		//if (!graphnodeList.contains(startLabel))
			//return null;
		
		graphnodeList.addFirst(startLabel); //Adds the start label to conform output.
				
		return graphnodeList;
		
	} //end cyclicPath
	
	/**
	 * Auxiliary method that uses recursion to determine if a path is cyclic.
	 * 
	 * @param n The next node to perform a modified dfs on.
	 * @param graphnodeList The list that contains the cyclic path; else null.
	 */
	private boolean cyclicPath(Graphnode n, LinkedList<String> path) {
		
		 n.setVisitFlag(true);
		 
		    for (Graphnode m : n.getSuccessors()) {
		    	
		        if (m.isVisited()) {
		        	
		        	path.addFirst(m.getLabel());
		        	
		            return true;
		        }
		        
		        if (!m.isVisited()) {

		        	if (cyclicPath(m, path)) {

		        		path.addFirst(m.getLabel());

		        		return true;
		        	}

		        }
		    }
		    
		    path.remove(n.getLabel());
		    
		    return false;

	} //end cyclicPath
	
	/**
	 * Find the shortest path from a start node to a finish node. Whenever 
	 * a node has multiple successors, the successors are visited in 
	 * alphabetical order. Returns the complete list of node labels along the 
	 * path, with the start node label appearing first and the finish node label 
	 * appearing last.
	 * 
	 * @param startLabel Label of the start node.
	 * @param finishLabel Label of the finish node.
	 * @return Sequence of nodes along the path or null if there is no such path.
	 * @throws IllegalArgumentException If either label is null or if there are
	 * no nodes in the graph with the given labels.
	 */
	public List<String> shortestPath(String startLabel, String finishLabel) {
		
		if (startLabel == null || finishLabel == null || 
				!this.hasNode(startLabel) || !this.hasNode(finishLabel))
			throw new IllegalArgumentException("Null label or non-node.");
		
		/* Storage for the source node. */
		Graphnode startNode = this.graphNodes.get(startLabel);
		
		/* Storage for the target node. */
		Graphnode finishNode = this.graphNodes.get(finishLabel);
		
		/* Storage for the current graphnode. */
		Graphnode currentNode;
		
		/* Creates the list to store the bfs entries. */
		LinkedList<String> graphnodeList = new LinkedList<String>();
		
		/* Storage for the queue to process the bfs. */
		Queue<Graphnode> queue = new LinkedList<Graphnode>();
		
		this.markNodes(); //Marks all of the nodes unvisited and zeros distance.
		
		startNode.setVisitFlag(true);
		
		startNode.setPredecessor(null);
		
		queue.offer(startNode); //Enqueues the current node.
		
		while (!queue.isEmpty()) {
			
			currentNode = queue.poll(); //Dequeues the next node.
			
			for (Graphnode k : currentNode.getSuccessors()) {
				
				if (!k.isVisited()) {
					
					k.setVisitFlag(true);
						
					if (k.getDistance() < currentNode.getDistance() + 1) {
						
						k.setDistance(currentNode.getDistance() + 1);
						
						k.setPredecessor(currentNode.getLabel());
					}
					
					queue.offer(k);
				} //end if
				
			} //end forEach
		} //end while
		
		if (finishNode.getPredecessor() == "") //Returns null if no path.
			return null;
		
		this.determinePath(finishNode, graphnodeList);
		
		graphnodeList.addLast(finishLabel);
		
		return graphnodeList;

	} //end shortestPath
	
	/**
	 * Auxiliary method that uses recursion to follow the last node backwards
	 * to determine its best path.
	 * 
	 * @param n The node to start with.
	 * @param graphnodeList The list used to store the path.
	 * @return The next Graphnode to perform the predecessor extraction on.
	 */
	private Graphnode determinePath(Graphnode n, LinkedList<String> graphnodeList) {
		
		if (n == null || n.getPredecessor() == null)
			return null;
		
		graphnodeList.addFirst(n.getPredecessor());
		
		return determinePath(this.graphNodes.get(n.getPredecessor()), graphnodeList);
		
	} //end determinePath
	
	/**
	 * Returns the number of nodes in the graph.
	 * 
	 * @return Number of nodes in the graph.
	 */
	public int size() {
		
		return this.size;
		
	} //end size
	
	/**
	 * Return true if and only if the graph has size 0 (i.e., no nodes or edges). 
	 * 
	 * @return True if and only if the graph is empty.
	 */
	public boolean isEmpty() {
		
		if (this.size() > 0)
			return false;
		
		return true;
		
	} //end isEmpty()
	
	/**
	 * Return an iterator over the node labels in the graph. The labels are 
	 * returned in sorted (alphabetical) order.
	 * 
	 * @return Iterator over the node labels in the graph (in alphabetical order).
	 */
	public Iterator<String> iterator() {
		
		return this.graphNodes.keySet().iterator();

	} //end iterator
	
	/**
	 * This method marks all of the graphnodes in the graph unvisited by setting 
	 * the visit flag to false, distance to 0, the predecessor to "",
	 * and the progress flag to UNVISITED.
	 */
	private void markNodes() {
		
		for (Graphnode n : this.graphNodes.values()) {
			
			n.setVisitFlag(false);
			
			n.setProgress("UNVISITED");
			
			n.setDistance(0);
			
			n.setPredecessor("");
		}
		
	} //end markNodes

} //end BasicGraph