/* Gregory Thornton	
 * COSC 423 Fall 2018 11/28/18
 * Program 4 - Pager
 * LFU class to run LFU Page Algorithm
 * 
 */
import java.io.*;
import java.util.*;

public class LFU {
	public int current ,LFU,maxFrames,faults,pointer,size;
	private ArrayList<LFUEntry[]> output;
	private ArrayList<Object> ids,faultsTotal;
	private LFUEntry[] frames,empty;
	private File input;
	private Scanner fileRead;
	private FileInputStream f;
	private boolean newFrames;
	public LFU(File f) {
		input = f;
	}
	
	public void compute() {
		output = new ArrayList<LFUEntry[]>();
		ids = new ArrayList<Object>();
		faultsTotal = new ArrayList<>();
		try {
			f = new FileInputStream(input.getAbsolutePath());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		fileRead = new Scanner(f);			// Read in file
		while(fileRead.hasNextLine()) {
			ids.add(fileRead.nextInt());
		}
		newFrames = true;
		
		for(int i = 0; i < ids.size(); i++) {
			current = (int)ids.get(i);		// grab current value from file ArrayList
			if(current == -1) {
				newFrames = true;			//set flag to setup arrays
				ids.remove(i);				//remove for printing later
				faultsTotal.add(faults);
				faults = 0;					//keep track of faults
				i--;						//keep pointer on right value
				continue;
			}
			if(newFrames == true) {
				if(current > maxFrames) {
					maxFrames = current;
				}
				frames = new LFUEntry[current];
				empty = new LFUEntry[current];
				emmptyFill();				//fill empty array, used for print processing
				Fill();						//fill frames with zeros for print processing, error reduction
				
				pointer = 0;				//used to add array at beginning of frame start
				newFrames = false;			//reset new frame length flag
				ids.remove(i);
				i--;
				continue;
			}
			if(!checkFilled()) {			//check if all frames are filled for pointer
				if(!checkValue(current)) {  //if not filled and value is not in frames then add it
					faults++;
					frames[pointer] = new LFUEntry(current,i);
					pointer++;
					output.add(frames.clone());
				} else {
					output.add(empty.clone()); // otherwise put empty list clone
				}
				
			} else if(!checkValue(current)) {
				faults++;
				LFU = computeLFU(i);		   //get index of LFU value in frames
				frames[LFU] = new LFUEntry(current,i);
				output.add(frames.clone());
			} else {
				output.add(empty.clone());   //input empty list if values is in frames already
			}
		} 
	print();
}
	private void print() {
		System.out.println("Least Frequently Used Algortithm");
		System.out.println("---------------------------------------------------------------------------------------------------");
		for(int i = 0; i < ids.size(); i++) {
			if(ids.get(i) != " ") {
			System.out.printf("%-2s",ids.get(i));
			}
			else {
				System.out.printf("%-2s"," ");
			}
		}
		
		System.out.println();
		System.out.println("---------------------------------------------------------------------------------------------------");
		for(int i =0; i < maxFrames; i++) {			//maxframes at any given moment in array list
			for(int j = 0; j < output.size(); j++) {
				if(output.get(j).length > i) {
					if(output.get(j)[i].getid() == -1 ) { //print id values given
						System.out.printf("%-2s", " ");
					}else {
					System.out.printf("%-2d",output.get(j)[i].getid());
					}
				}else {
					System.out.printf("%-2s", " ");
				}
			}
			System.out.println();
		}
		for(int i =0; i < faultsTotal.size(); i++) {
			System.out.println("Faults in " + (i + 1)+ " Group:" + faultsTotal.get(i));
		}
		System.out.println("\n\n");
	}
	
	private void emmptyFill() {
		for(int i = 0; i < empty.length; i++) {
			empty[i] = new LFUEntry(-1,-1);		//put -1 as indicator for blanks when printing
		}
		
	}
	
	private void Fill() {
		for(int i = 0; i < frames.length; i++) {
			frames[i] = new LFUEntry(0,0);		//initiliaze to prevent null pointers when checking
		}
		
	}

	private boolean checkValue(int a) {
		boolean present = false;
		for(int i = 0; i < frames.length; i++) {
			if(frames[i].getid() == a) {  		//if value is in frames then increment access var
				frames[i].increment();
				present = true;
			}
		}
		return present;
	}
	
	private boolean checkFilled() {
		return (pointer > frames.length-1?true:false); //used to make sure frames are filled before using LFU
	}
	
	private int computeLFU(int turns) {
		int index = 0;
		double min = 0.0;
		double[] weights = new double[frames.length];
		
		for(int i = 0; i < weights.length; i ++) {
			weights[i] = frames[i].getFrequency(turns);
		}
		min = weights[0];
		for(int i = 0; i < frames.length; i ++) { //compae lFU to get index which has the lowest
			if(weights[i] < min) {
				index = i;
				min = weights[i];
			}
		}
		return index;
		
	}
}