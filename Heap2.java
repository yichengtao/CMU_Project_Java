/**
 * This is a data structure that implements a heap whose objects are edges
 * @author Yicheng Tao
 *
 */
public class Heap2 {
	
	public Edge[] A; 
	private int size;

	/**
	 * Constructor to make a heap
	 */
	public Heap2(int maxSize) {
		A = new Edge[maxSize];
	}

	/**
	 * Insert an edge
	 * @param g the edge to insert
	 * @throws Exception the heap is full
	 */
	public void insert(Edge g) throws Exception {
		if (size >= A.length) {
			throw new Exception("heap is full");
		}
		if (g != null) {
			size += 1;
			A[size - 1] = g;
			A[size - 1].setHeapIdx(size - 1);
			percUp(size - 1);
		}
	}

	/**
	 * Delete the minimum edge
	 * @return the minimum edge
	 * @throws Exception the heap is empty
	 */
	public Edge deleteMin() throws Exception {
		if (size < 1) {
			throw new Exception("heap is empty");
		}
		Edge tmp = A[0]; // find the minimum
		A[0] = A[size - 1]; // exchange the minimum with the last edge
		A[size - 1] = tmp;
		A[0].setHeapIdx(0);
		A[size - 1].setHeapIdx(size - 1);
		size -= 1;
		percDown(0);
		return A[size];
	}

	/**
	 * Maintain the heap property on the levels above index i
	 * @param i index
	 */
	public void percUp(int i) {
		while (i > 0 && A[i].getLength() < A[parent(i)].getLength()) {
			Edge tmp = A[i];
			A[i] = A[parent(i)];
			A[parent(i)] = tmp;
			A[i].setHeapIdx(i);
			A[parent(i)].setHeapIdx(parent(i));
			i = parent(i);
		}
	}

	/**
	 * Maintain the heap property on the levels below index i
	 * @param i index
	 */
	public void percDown(int i) {
		while (true) {
			int smallest = i; // find the smallest between this, right child, and left child
			if (left(i) <= size - 1 && A[left(i)].getLength() < A[smallest].getLength()) {
				smallest = left(i);
			}
			if (right(i) <= size - 1 && A[right(i)].getLength() < A[smallest].getLength()) {
				smallest = right(i);
			}
			if (smallest != i) { // make the smallest be the parent
				Edge tmp = A[i];
				A[i] = A[smallest];
				A[smallest] = tmp;
				A[i].setHeapIdx(i);
				A[smallest].setHeapIdx(smallest);
				i = smallest;
			} else {
				break;
			}
		}
	}

	/**
	 * return the index of the left child
	 * @param i
	 * @return index of the left child
	 */
	public int left(int i) {
		return 2 * i + 1;
	}

	/**
	 * return the index of right child
	 * @param i
	 * @return index of the right child
	 */
	public int right(int i) {
		return 2 * i + 2;
	}

	/**
	 * return the index of the parent child
	 * @param i
	 * @return index of the parent child
	 */
	public int parent(int i) {
		return (int) ((i - 1) / 2);
	}
	
	/**
	 * Get the size of the heap
	 * @return size
	 */
	public int getSize() {
		return size;
	}
	
}
