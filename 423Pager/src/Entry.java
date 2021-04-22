/* Gregory Thornton	
 * COSC 423 Fall 2018 11/28/18
 * Program 4 - Pager
 * Entry class holds id value of added process
 * as well as current status as LFU in the frames
 * 
 */
public class Entry {
	private int id, status;
	
	public Entry(int a, int b) {
		id = a;
		status = b;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus( int a) {
			status = a;	
	}
	
	public int getid() {
		return id;
	}
	
}
