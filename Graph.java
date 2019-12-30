/**
 * This is a graph data structure.
 * @author Yicheng Tao
 *
 */
public class Graph {

	private Vertex[] vertices;
	private Edge[][] edges;
	 
	public Graph(Vertex[] vertices, Edge[][] edges) {
		this.vertices = vertices;
		this.edges = edges;
	}

	/**
	 * Use Dijkstra's algorithm to find the shortest path
	 * @param start the start vertex
	 * @param end the end vertex
	 * @param doMinimizeTime true to minimize time
	 * @return the shortest path
	 * @throws Exception
	 */
	public SinglyLinkedList<Edge> findShortestPath(Vertex start, Vertex end, boolean doMinimizeTime) throws Exception {
    	// Initialize the heap
		Heap H = new Heap(vertices.length);
	    for (int i = 0; i < vertices.length; i++) {
	    	H.insert(vertices[i]);
	    }
	    // Initialize the start vertex
	    H.decrease(start.getHeapIdx(), 0);
	    while (H.getSize() > 0) {
	    	Vertex v = H.deleteMin();
	    	if (v == end) {
	    		return v.getPath(); // return the shortest path
	    	}
	    	SinglyLinkedNode<Vertex> curr = v.getAdjList().getHead();
	    	for (int i = 0; i < v.getAdjList().getSize(); i++) {
	    		Vertex w = curr.getData();
	    		if (w.getHeapIdx() < H.getSize()) { // w is still in the heap
	    			int oldDist = w.getDist();
	    			if (!doMinimizeTime) { // update the shortest distance for w
	    				H.decrease(w.getHeapIdx(), v.getDist() + edges[v.getIdx()][w.getIdx()].getLength());
	    			} else {
	    				H.decrease(w.getHeapIdx(), v.getDist() + edges[v.getIdx()][w.getIdx()].getTime());
	    			}
	    			if (oldDist != w.getDist()) { // update the shortest path for w
	    				w.setPath(new SinglyLinkedList<Edge>());
	    				SinglyLinkedNode<Edge> curr2 = v.getPath().getHead();
	    				for (int j = 0; j < v.getPath().getSize(); j++) {
	    					w.extendPath(curr2.getData());
	    					curr2 = curr2.getNext();
	    				}
	    				w.extendPath(edges[v.getIdx()][w.getIdx()]);
	    			}
	    		}
	    		curr = curr.getNext();
	    	}
	    }
	    return null;
	}
}
