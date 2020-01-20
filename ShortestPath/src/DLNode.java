
public class DLNode<T>{
	private T dataItem;
	private DLNode<T> next;
	private DLNode<T> previous;
	private int value;
	
	public DLNode(T data, int value) {
		this.dataItem = data;
		this.value = value;
		
	}

	public T getDataItem() {
		return dataItem;
	}

	public void setDataItem(T dataItem) {
		this.dataItem = dataItem;
	}

	public DLNode<T> getNext() {
		return next;
	}

	public void setNext(DLNode<T> next) {
		this.next = next;
	}

	public DLNode<T> getPrevious() {
		return previous;
	}

	public void setPrevious(DLNode<T> previous) {
		this.previous = previous;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
