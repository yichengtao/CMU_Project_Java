import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Map {
	
	public static final int MaxString = 100;     // Maximum length of any input string.
	public static final int MaxLabel = 5;        // Maximum length of a location label (includes ending \0).
	public static final int MaxVertex = 175;     // Maximum number of vertices.
	public static final int MinVertex = 5;       // Smallest number of a vertex that is real (not 0 or a map corner).
	public static final int MaxEdge = 600;       // Maximum number of edges.
	public static final int MinEdge = 20;        // Smallest number of an edge that is real (not to 0 or a map corner).
	public static final int InfiniteCost = 10000; // Cost of an edge that does not exist.

	public static final double WalkSpeed = 272;    // ft/min = (3.1 miles/hr) * (5280 ft/mile) / (60 mins/hr)*/
	public static final double WalkFactorU = 0.9;  // Multiply walk speed by this for walk up.*/
	public static final double WalkFactorD = 1.1;  // Multiply walk speed by this for walk down.*/
	public static final double SkateFactorU = 1.1; // Multiply walk speed by this for skateboard up.*/
	public static final double SkateFactorF = 2.0; // Multiply walk speed by this for skateboard flat.*/
	public static final double SkateFactorD = 5.0; // Multiply walk speed by this for skateboard down.*/
	public static final double StepFactorU = 0.5;  // Multiply walk speed by this for walk up steps.*/
	public static final double StepFactorD = 0.9;  // Multiply walk speed by this for walk down steps.*/
	public static final double BridgeFactor = 1.0; // Multiply walk speed by this for walking on a bridge.*/
	
	public static final int MapWidthFeet = 5521; /*Width in feet of map.*/
	public static final int MapHeightFeet = 4369; /*Height in feet of map.*/
	public static final int MapWidthPixels = 2528; /*Width in pixels of map.*/
	public static final int MapHeightPixels = 2000; /*Height in pixels of map.*/
	public static final int CropLeft = 150; /*Pixels cropped from left of map.*/
	public static final int CropDown = 125; /*Pixels cropped from top of map.*/
	
	public static void main(String[] args) throws Exception {
		// I/O
		Scanner in = new Scanner(System.in);
		System.out.println("\n\n************* WELCOME TO THE BRANDEIS MAP *************");
		System.out.print("Enter start (return to quit): ");
		String startName = in.nextLine();
		if (startName.equals("")) {
			in.close();
			return;
		}
		System.out.print("Enter finish (or return to do a tour): ");
	    String endName = in.nextLine();
	    if (endName.equals("")) {
	    	in.close();
	    	return;
	    }
	    System.out.print("Have a skateboard (y/n - default=n)? ");
	    String bool = in.nextLine();
	    boolean boardFlag = false;
	    if (bool.equals("y")) {
	    	boardFlag = true;
	    }
	    System.out.print("Minimize time (y/n - default=n)? ");
	    bool = in.nextLine();
	    boolean doMinimizeTime = false;
	    if (bool.equals("y")) {
	    	doMinimizeTime = true;
	    }
	    System.out.println();
	    in.close();
	    // load the data and build a graph
	    String[] names = new String[152];
		String[] labels = new String[152];
		Vertex[] vertices = getVertices(names, labels);
		Edge[][] edges = getEdges(vertices, boardFlag);
		Graph g = new Graph(vertices, edges);
	    // find the start and the end point
	    Vertex start = null;
	    Vertex end = null;
	    for (int i = 0; i < names.length; i++) {
	    	if (names[i].toLowerCase().contains(startName.toLowerCase()) || labels[i].toLowerCase().equals(startName.toLowerCase())) {
	    		start = vertices[i];
	    	}
	    	if (names[i].toLowerCase().contains(endName.toLowerCase()) || labels[i].toLowerCase().equals(endName.toLowerCase())) {
	    		end = vertices[i];
	    	}
	    }
	    // find the shortest path according to length or time
	    if (start == null || end == null) {
	    	in.close();
	    	return;
	    }
	    SinglyLinkedList<Edge> shortestPath = g.findShortestPath(start, end, doMinimizeTime);
	    SinglyLinkedNode<Edge> curr = shortestPath.getHead();
	    // Output
	    int totalDist = 0;
	    int totalTime = 0;
	    PrintWriter writer1 = new PrintWriter("Route.txt", "UTF-8");
		PrintWriter writer2 = new PrintWriter("RouteCropped.txt", "UTF-8");
	    for (int i = 0; i < shortestPath.getSize(); i++) {
	    	// Output vertices and edges
	    	Edge currEdge = curr.getData();
	    	System.out.printf("FROM: (%s) %s\n", vertices[currEdge.getIdx1()].getLabel(), vertices[currEdge.getIdx1()].getName());
	    	if (currEdge.getName().length() > 0) {
	    		System.out.printf("ON: %s\n", currEdge.getName());
	    	}
	    	String prfxLen = "";
	    	String prfxTime = "";
	    	switch (currEdge.getCode()) {
	    		case "f" : prfxLen = "Walk"; prfxTime = (boardFlag) ? "no skateboards allowed, " : ""; break;
	    		case "x" :
	    		case "F" : prfxLen = (boardFlag) ? "Glide" : "Walk"; break;
	    		case "b" : prfxLen = "Walk"; prfxTime = (boardFlag) ? "no skateboards allowed, " : ""; break;
	    		case "u" : prfxLen =  "Walk up"; prfxTime = (boardFlag) ? "no skateboards allowed, " : ""; break;
	    		case "U" : prfxLen = (boardFlag) ? "Coast up" : "Walk up"; break;
	    		case "d" : prfxLen = "Walk down"; prfxTime = (boardFlag) ? "no skateboards allowed, " : ""; break;
	    		case "D" : prfxLen = (boardFlag) ? "Coast down" : "Walk down"; break;
	    		case "s" : prfxLen = "Go up"; prfxTime = (boardFlag) ? "no skateboards allowed, " : ""; break;
	    		case "t" : prfxLen = "Go down"; prfxTime = (boardFlag) ? "no skateboards allowed, " : ""; break;
	    	}
	    	System.out.printf("%s %d feet in direction %d degrees %s.\n", prfxLen, currEdge.getLength(), currEdge.getAngle(), currEdge.getDirection());
	    	totalDist += currEdge.getLength();
	    	System.out.printf("TO: (%s) %s\n", vertices[currEdge.getIdx2()].getLabel(), vertices[currEdge.getIdx2()].getName());
	    	// Output the time
	    	int time = calTime(currEdge, boardFlag);
	    	if (time >= 60) {
	    		System.out.printf("(%s%.1f minutes)\n", prfxTime, time / 60.0);
	    	} else {
	    		System.out.printf("(%s%d seconds)\n", prfxTime, time);
	    	}
	    	totalTime += time;
	    	// Write to the txt file
	    	int a = (int) (1.0 * vertices[currEdge.getIdx1()].getX() * MapHeightPixels / MapHeightFeet);
	    	int b = (int) (1.0 * vertices[currEdge.getIdx1()].getY() * MapWidthPixels / MapWidthFeet);
	    	int c = (int) (1.0 * vertices[currEdge.getIdx2()].getX() * MapHeightPixels / MapHeightFeet);
	    	int	d = (int) (1.0 * vertices[currEdge.getIdx2()].getY() * MapWidthPixels / MapWidthFeet);
	    	writer1.printf("%d %d %d %d\n", a, b, c, d);
	    	writer2.printf("%d %d %d %d\n", a - CropLeft, b - CropDown, c - CropLeft, d - CropDown);
	    	// To the next
	    	System.out.println();
	    	curr = curr.getNext();
	    }
	    writer1.close();
	    writer2.close();
	    // Output the stats
	    if (totalTime >= 60) {
	    	System.out.printf("legs = %d, distance = %d feet, time = %.1f minutes\n", shortestPath.getSize(), totalDist, totalTime / 60.0);
    	} else {
    		System.out.printf("legs = %d, distance = %d feet, time = %d seconds\n", shortestPath.getSize(), totalDist, totalTime);
    	}
	}
	
	/**
	 * Loads the vertices data
	 * @param names the names of vertices
	 * @param labels the labels of vertices
	 * @return a array of vertices
	 */
	public static Vertex[] getVertices(String[] names, String[] labels) {
		Vertex[] vertices = new Vertex[152];
		try {
			Scanner sc = new Scanner(new File("MapDataVertices.txt"));
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.length() > 0 && line.charAt(0) >= 48 && line.charAt(0) <= 57) {
					String[] info = line.split("\"");
					String[] info1 = info[0].trim().split(" ");
					vertices[Integer.parseInt(info1[0])] = new Vertex(Integer.parseInt(info1[0]), info1[1], Integer.parseInt(info1[2]), Integer.parseInt(info1[3]), info[1]);
					names[Integer.parseInt(info1[0])] = info[1];
					labels[Integer.parseInt(info1[0])] = info1[1];
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return vertices;
	}
	
	/**
	 * Loads the edges data
	 * @param vertices the array of vertices
	 * @param boardFlag true if has skateboard
	 * @return an array of edges
	 */
	public static Edge[][] getEdges(Vertex[] vertices, boolean boardFlag) {
		Edge[][] edges = new Edge[152][152];
		try {
			Scanner sc = new Scanner(new File("MapDataEdges.txt"));
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.length() > 0 && line.charAt(0) >= 48 && line.charAt(0) <= 57) {
					String[] info = line.split("\"");
					String name = "";
					if (info.length > 1) {
						name = info[1];
					}
					String[] info1 = info[0].trim().split(" ");
					Edge currEdge = new Edge(Integer.parseInt(info1[3]), Integer.parseInt(info1[4]), Integer.parseInt(info1[5]), Integer.parseInt(info1[6]), info1[7], info1[8].substring(1, info1[8].length() - 1), name);
					currEdge.setTime(calTime(currEdge, boardFlag));
					edges[Integer.parseInt(info1[3])][Integer.parseInt(info1[4])] = currEdge;
					vertices[Integer.parseInt(info1[3])].addNeibor(vertices[Integer.parseInt(info1[4])]);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return edges;
	}
	
	/**
	 * Calculate the time used to pass an edge
	 * @param edge the edge to pass
	 * @param boardFlag true if with skateboard
	 * @return the time used in seconds
	 */
	public static int calTime(Edge edge, boolean boardFlag) {
		int time = (int) ((60 * ((double) edge.getLength() / (double) WalkSpeed)) + 0.5);
    	switch (edge.getCode()) {
    		case "f" : break;
    		case "x" :
    		case "F" : time /= (boardFlag) ? SkateFactorF : 1; break;
    		case "b" : time /= BridgeFactor; break;
    		case "u" : time /= WalkFactorU; break;
    		case "U" : time /= (boardFlag) ? SkateFactorU : WalkFactorU; break;
    		case "d" : time /= WalkFactorD; break;
    		case "D" : time /= (boardFlag) ? SkateFactorD : WalkFactorD; break;
    		case "s" : time /= StepFactorU; break;
    		case "t" : time /= StepFactorD; break;
    	}
		return time;
	}
	
}