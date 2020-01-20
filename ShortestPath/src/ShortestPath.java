import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Cole Chang
 * cchan948
 * Student Number: 251038024
 * Professor: Roberto Sollis-Oba
 */
/** 
 * This class is designed to find the shortest path from the Western Power Company (WPC) to the house of the customer, using Doubly Linked Lists 
 * 
 * many variables that will be used throughout the algorithm are initialized and declared here
 * the map object (the object that is being traversed by the power) and the DL
 */
public class ShortestPath {
	Map cityMap;
	
	// customer for ShortestPath, takes filename of requested map
	public ShortestPath(String filename) {
		try{
			cityMap = new Map(filename);
		}
		/**
		 * catches several possible exceptions
		 * FileNotFound
		 * InvalidMapException
		 * IOException
		 */
		catch (FileNotFoundException e) {
			System.out.println("Sorry, the file was not found:");
			e.printStackTrace();
		} 
		catch (InvalidMapException e) {
			System.out.println("An unknown map cell was used");
			e.printStackTrace();
		} 
		catch (IOException e) {
			System.out.println("Sorry, unable to use that file:");
			e.printStackTrace();
		}
	}
	//create an object of the class shortest path using the constructor
	// the main method will try to find the shortest path to the destination cell
	// the algorithm that checks for all available routes at any given spot
	public static void main (String[] args) {
		DLList<MapCell> list = new DLList<MapCell>();
		
		ShortestPath path = new ShortestPath(args[0]);
		
		MapCell cell = path.cityMap.getStart();
	//marks the first cell, the power station
		list.insert(cell, 0);
		cell.markInitial();
		boolean customer = false;
		int distance = 0;
		int totaldistance = 0;
		int counter = 0;
		MapCell finalcell = null;
	try {	
		/** evaluates all the neighbors of the current cell in order to determine the next possible positions.
		 * Viable positions are inserted into the linked list and will be evaluated on the next iteration of the loop
		 */
		while(!list.isEmpty()) {
			MapCell smallestcell = list.getSmallest();
			smallestcell.markOutList();
			
			if (path.nextCell(smallestcell) != null) {
				if(path.nextCell(smallestcell).isCustomer()){
					path.nextCell(smallestcell).markCustomer();
					finalcell = path.nextCell(smallestcell);
					customer = true;
				}
			distance = 1 + smallestcell.getDistanceToStart();
			
			for (int b = 0; b <= 3; b++) {
			if (path.nextCell(smallestcell) != null) {
				if(path.nextCell(smallestcell).getDistanceToStart() > distance) {
					path.nextCell(smallestcell).setDistanceToStart(distance);
					path.nextCell(smallestcell).setPredecessor(smallestcell);
			}
			totaldistance = path.nextCell(smallestcell).getDistanceToStart();
			
				if (path.nextCell(smallestcell).isMarkedInList() && totaldistance < list.getDataValue(smallestcell)) {
					list.changeValue(smallestcell, totaldistance);
			}
			//inserts all the neighboring cells into the list 
				if(!path.nextCell(smallestcell).isMarkedInList()) {
					list.insert(path.nextCell(smallestcell), totaldistance);
					path.nextCell(smallestcell).markInList();
						}
					}
			}
			}
			//finds the number of cell used to find the best path
			if(customer == true){
				
				while(finalcell.isPowerStation() == false){
					finalcell = finalcell.getPredecessor();
					counter++;
				}
			}
		}
		//prints out if the customer cell was reached and in how many cells
		//or if not prints that the customer cell was not reached
		if(customer == true) {
			System.out.println("The number of cells taken to reach the customer cell is: " + counter);
		}
		else {
			System.out.println("Destination was not reached.");
		}
	}
	//catches the type of error found
	catch(NullPointerException e) {
		System.out.println("cell not in linked list");
		e.printStackTrace();
	}
	//data item not found in linked list exception
	catch(InvalidDataItemException e) {
		System.out.println("Invalid Data Item");
		e.printStackTrace();
	}
	//Incorrect argument exception in linked list
	catch(IllegalArgumentException e) {
		System.out.println("No Command Line provided");
		e.printStackTrace();
	}
	// an invalid map was provided
	catch(InvalidMapException e) {
		System.out.println("Invalid map entered");
		e.printStackTrace();
	}
	//linked list is empty exception
	catch(EmptyListException e) {
		System.out.println("List empty");
		e.printStackTrace();
	}
	//referencing a neighbor that is not there or is not valid
	catch(InvalidNeighbourIndexException e) {
		System.out.println("Referencing invalid neighbour");
		e.printStackTrace();
	}
	}
	// provides the main method with the next cell in the path
	private MapCell nextCell(MapCell cell) {
		//checks all the surrounding neighbors around the original cell
		MapCell next = null;
		//iterates to all the surrounding neighbors of the cell
		for (int i = 0; i <= 3; i++) {
			
			if (cell.getNeighbour(i) != null) {
				//checks all conditions based on what type of switch the cell is

				if(cell.getNeighbour(i).isMarked() == false) {
					//if the cell is a vertical switch checks the north and south cells to see if the power can go there
					if(cell.isVerticalSwitch() && (i == 0 || i == 2) && (cell.getNeighbour(i).isVerticalSwitch() || cell.getNeighbour(i).isOmniSwitch()) || cell.getNeighbour(i).isCustomer()) {
						next = cell.getNeighbour(i);
						break;
						
					}
					//if the cell is a horizontal switch checks the east and west cells to see if the power can go there
					if(cell.isHorizontalSwitch() && (i == 1 || i == 3) && (cell.getNeighbour(i).isHorizontalSwitch() || cell.getNeighbour(i).isOmniSwitch()) || cell.getNeighbour(i).isCustomer()) {
						next = cell.getNeighbour(i);
						break;
						
					}
					//if the cell is a omni switch checks the north and south cells to see if either a vertical or omni switch is there for the power to go there
					if(cell.isOmniSwitch() && (i == 0 || i == 2) && (cell.getNeighbour(i).isVerticalSwitch() || cell.getNeighbour(i).isOmniSwitch()) || cell.getNeighbour(i).isCustomer()) {
						next = cell.getNeighbour(i);
						break;
						
					}
					//if the cell is a omni switch checks the east and west cells to see if either a vertical or omni switch is there for the power to go there
					if(cell.isOmniSwitch() && (i == 1 || i == 3) && (cell.getNeighbour(i).isHorizontalSwitch() || cell.getNeighbour(i).isOmniSwitch()) || cell.getNeighbour(i).isCustomer()) {
						next = cell.getNeighbour(i);
						break;
						
					}
					//if the cell is the power station checks to see if either a vertical or omni switch is at the north or south locations
					if(cell.isPowerStation() && (i == 0 || i == 2) && (cell.getNeighbour(i).isOmniSwitch() || cell.getNeighbour(i).isVerticalSwitch())){
						next = cell.getNeighbour(i);
						break;
					}
					//if the cell is the power station, the case checks to see if either a horizontal or omni switch is at the east or west locations
					if(cell.isPowerStation() && (i == 1 || i == 3) &&(cell.getNeighbour(i).isHorizontalSwitch() || cell.getNeighbour(i).isOmniSwitch())){
						next = cell.getNeighbour(i);
						break;
					}
			}
			}
		}
		return next;
	}
}
