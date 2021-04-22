/* Gregory Thornton	
 * COSC 423 Fall 2018 11/28/18
 * Program 4 - Pager
 * Optimal class to run Optimal Page Algorithm
 * 
 */
import java.io.*;
import java.util.*;

public class Optimal {

	private int current ,maxFrames,faults,pointer,replaceIndex;
	private ArrayList<int[]> output;
	private ArrayList<Object> ids,faultsTotal;
	private int[] frames,empty;
	private File input;
	private Scanner fileRead;
	private FileInputStream f;
	private boolean newFrames;
	public Optimal(File f) {
		input = f;
	}
	
	public void compute() {
			output = new ArrayList<int[]>();
			ids = new ArrayList<Object>();
			faultsTotal = new ArrayList<>();
			try {
				f = new FileInputStream(input.getAbsolutePath());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("File Cant be Found" + e1.getMessage());
			}
			fileRead = new Scanner(f);
			while(fileRead.hasNextLine()) {		//read values to array list to process
				ids.add(fileRead.nextInt());
			}
			newFrames = true;
			
			for(int i = 0; i < ids.size(); i++) {
				current = (int)ids.get(i);
				if(current == -1) {				//set flag to resetup resources and skip to next iteration
					newFrames = true;
					faultsTotal.add(faults);
					faults = 0;					//keep track of faults
					ids.set(i,-1);
					output.add(empty.clone());
					output.add(empty.clone());
					continue;
				}
				if(newFrames == true) {
					if(current > maxFrames) {
						maxFrames = current;	//maxFrames, used to calculate how deep an array is when printing
					}
					frames = new int[current];//array to hold frame id, and LRU status
					empty = new int[current]; //array for print formatting
					emmptyFill();				//fill empty array, used for print processing
					Fill();						//fill frames with zeros for print processing, error reduction
					pointer = 0;				//used to add array at beginning of frame start
					newFrames = false;
					continue;
				}
				if(!checkFilled()) {
					faults++;
					frames[pointer] = current;
					pointer++;
					output.add(frames.clone());
				} else if(!checkValue(current)) {
					faults++;
					replaceIndex = crystalBall(i);		//calc the farthest idex used
					frames[replaceIndex] = current ;	//replace that val in the current frames
					output.add(frames.clone());
				} else {
					output.add(empty.clone()); //input empty list if values is in frames already
				}
			} 
		print();
	}
	private void print() {
		System.out.println("Optimal Algortithm");
		System.out.println("---------------------------------------------------------------------------------------------------");
		for(int i = 1; i < ids.size(); i++) {
			if(ids.get(i) != " " || (int)ids.get(i) != -1) {
				System.out.printf("%-2d",(int)ids.get(i));
			}else { 
				System.out.printf("%-2s"," ");
			}
		}
		System.out.println();
		System.out.println("---------------------------------------------------------------------------------------------------");
		for(int i =0; i < maxFrames; i++) {				//maxframes at any given moment in array list
			for(int j = 0; j < output.size(); j++) {
				if(output.get(j).length > i) {
					if(output.get(j)[i] == -1 ) {  		//print id values give
						System.out.printf("%-2s", " ");
					}else {
					System.out.printf("%-2d",output.get(j)[i]);
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
			empty[i] = -1;				//put -1 as indicator for blanks when printing
		}
		
	}
	
	private void Fill() {
		for(int i = 0; i < frames.length; i++) { //initiliaze to prevent null pointers when checking
			frames[i] = 0;
		}
		
	}

	private boolean checkValue(int a) {
		boolean present = false;
		for(int i = 0; i < frames.length; i++) { //if value is in frames then 
			if(frames[i] == a) {
				present = true;
			}
		}
		return present;
	}
	
	private boolean checkFilled() {
		return (pointer > frames.length-1?true:false);//used to make sure frames are filled before using crystalBall
	}
	
	private int crystalBall(int current) {
		int index = 0,max = 0;
		int[] weights = new int[frames.length];
		
		for(int i = 0; i < frames.length; i ++) {
			for( int k = current+1; k < ids.size(); k++) {
				if(((int)ids.get(k)) == frames[i]) {
					weights[i] = (k-current);		//get index of next appearance for value
					break;
				}
			}
		}
		
		for(int i = 0; i < frames.length; i ++) {
			if(weights[i] == 0) {
				index = i;
				break;
			} else if(weights[i] > max) {		//compare which has the biggest next appearance value
				index = i;
				max = weights[i];
			}
		}
		return index;
		
	}
}
