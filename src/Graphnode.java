///////////////////////////////////////////////////////////////////////////////
//
// Main Class File:  GraphAnalyser.java
// File:             Graphnode.java
// Semester:         Spring 2012
//
// Author:           Corbin Schwalm
// CS Login:         schwalm
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

import java.util.TreeSet;

/**
 * The Graphnode class represents the nodes for a directed graph. Node labels 
 * are Strings and are assumed to be unique.
 * 
 * @author Corbin Schwalm
 */
public class Graphnode implements Comparable<Graphnode> {
	
	/** The label/identifier for the node. */
	private String label;
	
	/** Flag to mark if the current node has been visited. */
	private boolean visitFlag;
	
	/** Storage for the progress flag. E.g. UNVISITED, IN_PROGRESS, DONE. */
	private String progress;
	
	/** Storage for the distance variable. Can't be negative. */
	private int distance;
	
	/** The label of the predecessor node. To determine shortest path. */
	private String predecessor;
	
	/** Storage for the successor nodes of this node. */
	private TreeSet<Graphnode> adjancyList;
	
	/**
	 * Constructs a Graphnode for the specified label.
	 * 
	 * @param label The label name.
	 */
	public Graphnode(String label) {
		
		this.label = label;
		
		this.visitFlag = false;
		
		this.progress = "UNVISITED";
		
		this.distance = 0;
		
		this.predecessor = "";
		
		this.adjancyList = new TreeSet<Graphnode>();
		
	} //end constructor
	
	/**
	 * Gets the label/name of the node.
	 * 
	 * @return The label.
	 */
	public String getLabel() {
	
		return this.label;
		
	} //end getLabel
	
	/**
	 * Determines if the node has been visited.
	 * 
	 * @return True if visited; false otherwise.
	 */
	public boolean isVisited() {
	
		return this.visitFlag;
		
	} //end isVisited
	
	/**
	 * Sets the visit flag of the current node.
	 * 
	 * @param visitFlag The new visit flag.
	 */
	public void setVisitFlag(boolean visitFlag) {
	
		this.visitFlag = visitFlag;
		
	} //end setVisitFlag

	/**
	 * Returns the progress flag to determine if the node is still on a stack.
	 * 
	 * @return The progress. E.g. UNVISITED, IN_PROGRESS, DONE.
	 */
	public String getProgress() {
	
		return this.progress;
		
	} //end getProgress

	/**
	 * Sets the progress flag. Acceptable values are 
	 * UNVISITED, IN_PROGRESS, DONE, PREDECESSOR.
	 * 
	 * @param progress The progress to set.
	 */
	public void setProgress(String progress) {
	
		this.progress = progress;
		
	} //end setProgress

	/**
	 * Gets the distance for the current node. Used to calculate shortest path.
	 * 
	 * @return The distance. Can't be negative.
	 */
	public int getDistance() {
	
		return this.distance;
		
	} //end getDistance
	
	/**
	 * Sets the distance for the current node. Used to calculate shortest path.
	 * 
	 * @param distance The distance to set. Can't be negative.
	 */
	public void setDistance(int distance) {
	
		this.distance = distance;
		
	} //end setDistance

	/**
	 * Gets the predecessor for the current node. 
	 * Used to calculate shortest path.
	 * 
	 * @return The label of the predecessor node that has shortest path.
	 */
	public String getPredecessor() {
	
		return this.predecessor;
		
	} //end getPredecessor
	
	/**
	 * Sets the predecessor for the current node. 
	 * Used to calculate shortest path.
	 * 
	 * @param predecessor The label of the predecessor node of shortest distance.
	 */
	public void setPredecessor(String predecessor) {
	
		this.predecessor = predecessor;
		
	} //end setPredecessor

	/**
	 * Returns the adjancyList which contains the list of successors of the node.
	 * @return The adjancyList.
	 */
	public TreeSet<Graphnode> getSuccessors() {
	
		return this.adjancyList;
		
	} //end getSuccessors
	
	/**
	 * Returns a string representation of the current node. I.E. the label.
	 * 
	 * @return The label of the node.
	 */
	public String toString() {
		
		return this.getLabel();
		
	} //end toString

	/**
	 * Compares two Graphnode objects. Uses their label name for comparison.
	 * 
	 * @param other Graphnode for comparison.
	 * @return <0 if other is less than current node; 0 if equal; otherwise >1.
	 * @throws IllegalArgumentException if other is not a Graphnode object.
	 */
	public int compareTo(Graphnode other) {
		
		return this.getLabel().compareToIgnoreCase(other.getLabel());
		
	} //end compareTo
	
	/**
	 * Determines if two Graph node objects are equal. Uses label names for 
	 * comparison.
	 * 
	 * @param other The other Graphnode.
	 * @return True if equal; false otherwise.
	 * @throws IllegalArgumentException If other is not a Graphnode object.
	 */
	public boolean equals(Graphnode other) {
		
		return this.getLabel().equals(other.getLabel());
		
	} //end equals
	
} //end Graphnode