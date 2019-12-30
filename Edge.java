/**
 * This is a data structure that implements an Brandeis map edge
 * @author Yicheng Tao
 *
 */
public class Edge {

	private int idx1;
	private int idx2;
	private int length;
	private int angle;
	private String direction;
	private String code;
	private String name;
	private int time;
	private int heapIdx;
	
	public Edge (int idx1, int idx2, int length, int angle, String direction, String code, String name) {
		this.idx1 = idx1;
		this.idx2 = idx2;
		this.length = length;
		this.angle = angle;
		this.direction = direction;
		this.code = code;
		this.name = name;
	}
	
	public int getIdx1() {
		return idx1;
	}
	
	public int getIdx2() {
		return idx2;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getAngle() {
		return angle;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public int getHeapIdx() {
		return heapIdx;
	}
	
	public void setHeapIdx(int heapIdx) {
		this.heapIdx = heapIdx;
	}
	
}
	
	
