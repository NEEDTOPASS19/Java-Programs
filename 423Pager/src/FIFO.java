/* Gregory Thornton	
 * COSC 423 Fall 2018 11/28/18
 * Program 4 - Pager
 * FIFO class to run FIFO Page Algorithm
 * 
 */
import java.io.*;
import java.util.*;

public class FIFO {
	private int current ,pointer,maxFrames,faults;
	private ArrayList<int[]> output;
	private ArrayList<Object> ids,faultsTotal;
	private int[] frames,empty;
	private File input;
	private Scanner fileRead;
	private FileInputStream f;
	
	public FIFO(File f) {
		input = f;
	}
	
	public void compute() {
			output = new ArrayList<int[]>();
			ids = new ArrayList<>();
			faultsTotal = new ArrayList<>();
			fileRead = new Scanner(input.getAbsolutePath());
			try {
				//initial setup
				f = new FileInputStream(input.getAbsolutePath());
				fileRead = new Scanner(f);
				frames = new int[fileRead.nextInt()];
				empty = new int[frames.length];
				maxFrames = frames.length;
				emmptyFill();
				pointer = 0;
				faults = 0;
				while(fileRead.hasNextInt()) {
					current = fileRead.nextInt();
					
					if(current == -1) {
						faultsTotal.add(faults);
						faults = 0;
						if(!fileRead.hasNextLine()) {	//break loop when end is reached
							break;
						} 
						current = fileRead.nextInt();
						if(current > maxFrames) {
							maxFrames = current;	//maxFrames, used to calculate how deep an array is when printing
						}
						output.add(empty);
						output.add(empty);
						frames = new int[current];
						empty = new int[current];
						emmptyFill();			//fill placeholder
						pointer = 0;
						current = fileRead.nextInt();
						ids.add(" ");			//Add spaces for ease of reading when printing
						ids.add(" ");
					}
					ids.add(current);
					if(!checkValue(current)) {
						faults++;
						frames[pointer] = current;
						output.add(frames.clone());		//copy of frame image for output
						pointer = (pointer+ 1) % frames.length;	//pointer to point at next replaced frame
					} else {
						output.add(empty);		//empty placeholder for printing
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			print();
		
	}	
	private void print() {
		System.out.println("First Come First Serve Algortithm");
		System.out.println("---------------------------------------------------------------------------------------------------");
		for(int i = 0; i < ids.size(); i++) {
			if(ids.get(i) != " ") {
			System.out.printf("%-2s",ids.get(i));
			}
			else System.out.printf("%-2s"," ");
		}
		System.out.println();
		System.out.println("---------------------------------------------------------------------------------------------------");
		for(int i =0; i < maxFrames; i++) {
			for(int j = 0; j < output.size(); j++) {
				if(output.get(j).length > i) {
					if(output.get(j)[i] == -1) {
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

	private boolean checkValue(int a) {
		boolean present = false;
		for(int i = 0; i < frames.length; i++) { //if value is in frames then 
			if(frames[i] == a) {
				present = true;
			}
		}
		return present;
	}
}
