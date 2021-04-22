/* Gregory Thornton	
 * COSC 423 Fall 2018 11/28/18
 * Program 4 - Pager
 * Pager class to run as a driver for all other classes
 * 
 */
import java.io.*;

public class Pager {

	public static void main(String[] args) {
		File data = new File("PagerInput");
		LFU lfu = new LFU(data);
		LRU lru = new LRU(data);
		FIFO f = new FIFO(data);
		Optimal O = new Optimal(data);
		O.compute();
		lfu.compute();
		lru.compute();
		f.compute();	
	}

}
