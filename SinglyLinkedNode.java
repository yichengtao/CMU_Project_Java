/**
 * This is data structure that implements a singly linked list node.
 * @author Yicheng Tao
 *
 * @param <T>
 */

public class SinglyLinkedNode<T> {

	private T data;
	private SinglyLinkedNode<T> next;

	public SinglyLinkedNode(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setNext(SinglyLinkedNode<T> nextNode) {
		next = nextNode;
	}

	public SinglyLinkedNode<T> getNext() {
		return next;
	}
	
}
