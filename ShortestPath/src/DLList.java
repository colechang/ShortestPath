/** 
 * @author Cole Chang
 *
 * Student ID: cchan948
 * Student Number: 251038024
 * Professor: Roberto Sollis-Oba
 * 
 * This class represents the nodes of a doubly linked list. Each node stores two items, a data item and an associated integer value.
 */

public class DLList<T> implements DLListADT<T>{
	private DLNode<T> front; // reference to 1 node of the doubly linked list
	private DLNode<T> rear; // reference to the last node of the doubly linked list
	private int count; // number of nodes in the linked list
	
	//creates empty DLList
	public DLList(){
		count = 0;
	}

	// adds a new node to the rear of the linked list
	public void insert(T dataItem, int value) {
		DLNode<T> node = new DLNode<T>(dataItem, value);
		
		if(isEmpty()) {
			front = rear = node;
		}
		else {
			rear.setNext(node);
			node.setPrevious(rear);
			rear = node;
		}
		count ++;
	}

	// returns the data item of the lowest value
	public T getSmallest() throws EmptyListException {
		DLNode<T> node = front;
		DLNode<T> lowestNode = node;
		
		// if list is empty
		if (isEmpty()) {
			throw new EmptyListException ("Linked List is empty.");
		}
		
		else if (count == 1) {
			front = rear = null;
			count--;
			return node.getDataItem();
		}
		else {
			for(int i = 1;  i < count ; i ++){
				node = node.getNext();
				if (node.getValue() < lowestNode.getValue()){
					lowestNode = node;
				}
			}
			if (lowestNode.equals(front)){
				front = lowestNode.getNext();
				front.setPrevious(null);
				count --;
				return lowestNode.getDataItem();
			}else if (lowestNode.equals(rear)){
				rear = rear.getPrevious();
				rear.setNext(null);
				count --;
				return lowestNode.getDataItem();
			}else {
				lowestNode.getNext().setPrevious(lowestNode.getPrevious());
				lowestNode.getPrevious().setNext(lowestNode.getNext());
				count --;
				return lowestNode.getDataItem();
			}
		}	
	}
	//substitutes the new value of the requested data item
	public void changeValue(T dataItem, int newValue) throws InvalidDataItemException {
		DLNode<T> theNode = new DLNode<T>(dataItem, newValue);
		DLNode<T> currentNode = front;
		Boolean flag = true;
		
		for (int i = 0; i < count; i ++) {
			if ((currentNode.getDataItem()).equals(dataItem)) {
				flag = false;
			}
			currentNode= currentNode.getNext();
		}
		
		if (flag == true) {
			throw new InvalidDataItemException("Not in queue.");
		}
		
		if (isEmpty()) {
			throw new InvalidDataItemException("dataItem not in list.");
		}
		//throw exception for element that doesn't exist
	
		DLNode<T> node = front;
		int counter = 0;
		
		while (node != (null) && !node.getDataItem().equals(theNode.getDataItem())) {
			DLNode<T> temp = node.getNext();	
			node = temp;
			counter ++;
			if (counter == count) {
				throw new InvalidDataItemException ("dataItem not in list.");
			}
		}
		node.setValue(newValue);
	}
	//returns true or false if the linked list is empty
	public boolean isEmpty() {
		if (count == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	//returns the integer size of the linked list
	public int size() {
		return count;
	}
	//returns a string representation of the linked list
	public String toString() {
		String result = "";
		DLNode<T> current = front;
		result = result + current.getDataItem().toString();
		for (int i = 1; i < count; i++){
			current = current.getNext();
			result = result + ", " + current.getDataItem().toString();
		}
		return result;
	}

	public int getDataValue(T dataItem) throws InvalidDataItemException {
		DLNode<T> node = front;
		// linear search through linked list
		while (!node.getDataItem().equals(dataItem)) {
			node = node.getNext();
			
			// breaks out of while loop if loop has gone through whole list
			if (node.getNext().getDataItem().equals(null)) {
				break;
			}
		}
		
		// if dataItem not it list
		if (node.getDataItem().equals(null)) {
			throw new InvalidDataItemException ("datatItem not in list.");
		}
		
		else {
			return node.getValue();
		}
	}
}