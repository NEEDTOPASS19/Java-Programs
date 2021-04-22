/* Gregory Thornton	
 * COSC 423 Fall 2018 11/28/18
 * Program 4 - Pager
 * LFUEntry class holds the access info as well as when added to frames
 * also computes LFU frequency to pass to the LFU Pager class
 * 
 */
public class LFUEntry {

	private int id, added,access;
	
	public LFUEntry(int a, int b) {
		id = a;
		added = b;
		access = 0;
	}
	
	public double getFrequency( int currentaccess) {
		
		return (access == 0? 0 :(currentaccess-added)/access);
	}
	
	public void increment() {
		access++;
	}
	
	public int getid() {
		return id;
	}
	
}

