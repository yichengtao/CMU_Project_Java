/**
 * This is a data structure that implements a Brandeis map vertex
 * @author Yicheng Tao
 *
 */
public class Vertex {

	private int idx;
	private String label;
	private int x; // Coordinates (feet) are down and to the right from upper left corner.
	private int y;
	private String name;
	private SinglyLinkedList<Vertex> adjList; // adjacency list
	private int dist;
	private int heapIdx;
	private SinglyLinkedList<Edge> path; 
		
	public Vertex(int idx, String label, int x, int y, String name) {
		this.idx = idx;
		this.label = label;
		this.x = x;
		this.y = y;
		this.name = name;
		adjList = new SinglyLinkedList<Vertex>();
		this.dist = Integer.MAX_VALUE;
		path = new SinglyLinkedList<Edge>();
	}
	
	public int getIdx() {
		return idx;
	}
	
	public String getLabel() {
		return label;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getName() {
		return name;
	}
	
	public SinglyLinkedList<Vertex> getAdjList() {
		return adjList;
	}

	public void addNeibor(Vertex v) {
		adjList.insert(v);
	}
	
	public int getDist() {
		return dist;
	}
	
	public void setDist(int dist) {
		this.dist = dist;
	}
	
	public int getHeapIdx() {
		return heapIdx;
	}
	
	public void setHeapIdx(int idx) {
		heapIdx = idx;
	}
	
	public SinglyLinkedList<Edge> getPath() {
		return path;
	}

	public SinglyLinkedList<Edge> setPath(SinglyLinkedList<Edge> path) {
		return this.path = path;
	}
	
	public void extendPath(Edge v) {
		path.insert(v);
	}
	
}
