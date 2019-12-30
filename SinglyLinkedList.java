/**
 * This is a data structure that implements a singly linked list.
 * @author Yicheng Tao
 *
 * @param <T>
 */

public class SinglyLinkedList<T> {

	private SinglyLinkedNode<T> L;
	private SinglyLinkedNode<T> tail;
	private int size;

	public SinglyLinkedNode<T> getHead() {
		return L;
	}
	
	public SinglyLinkedNode<T> getTail() {
		return tail;
	}
	
	public int getSize() {
		return size;
	}

	/**
	 * Insert at the end
	 * @param data
	 */
	public void insert(T data) {
		SinglyLinkedNode<T> insertedNode = new SinglyLinkedNode<T>(data);
		if (size == 0) {
			L = insertedNode;
			tail = insertedNode;
		} else {
			tail.setNext(insertedNode);
			tail = insertedNode;
		}
		size++;
	}
	
	/**
	 * Get the node at the given index
	 * @param index
	 * @return
	 */
	public SinglyLinkedNode<T> get(int index) {
		if (index < size && index >= 0) {
			SinglyLinkedNode<T> curNode = L;
			for (int i = 0; i < index; i++) {
				curNode = curNode.getNext();
			}
			return curNode;
		}
		return null;
	}

}
