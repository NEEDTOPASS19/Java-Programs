/* Gregory Thornton	
 * COSC 423 Fall 2018 11/28/18
 * Program 4 - Pager
 * LRU class to run LRU Page Algorithm
 * 
 */
import java.io.*;
import java.util.*;

public class LRU {
	public int current ,LRU,maxFrames,faults,pointer,size;
	private ArrayList<Entry[]> output;
	private ArrayList<Object> ids,faultsTotal;
	private Entry[] frames,empty,clone;
	private File input;
	private Scanner fileRead;
	private FileInputStream f;
	
	public LRU(File f) {
		input = f;
	}
	
	public void compute() {
			output = new ArrayList<Entry[]>();
			ids = new ArrayList<>();
			faultsTotal = new ArrayList<>();
			try {
				//initial setup
				f = new FileInputStream(input.getAbsolutePath());
				fileRead = new Scanner(f);
				size = fileRead.nextInt()-1;
				frames = new Entry[size+1];
				empty = new Entry[frames.length];
				clone = new Entry[frames.length];
				maxFrames = frames.length;
				emmptyFill();
				Fill();
				LRU = 0;
				faults = 0;
				pointer = 0;
				while(fileRead.hasNextInt()) {		//read values to array list to process
					current = fileRead.nextInt();
					
					if(current == -1) {
						faultsTotal.add(faults);
						faults = 0;					//keep track of faults
						if(!fileRead.hasNextLine()) {
							break;
						} 
						current = fileRead.nextInt();
						if(current > maxFrames) {
							maxFrames = current;	//maxFrames, used to calculate how deep an array is when printing
						}
						output.add(empty.clone());
						output.add(empty.clone());
						size = current-1;
						frames = new Entry[current];//array to hold frame id, and LRU status
						empty = new Entry[current]; //array for print formatting
						clone = new Entry[current]; //array to clone current frames image
						emmptyFill();				//fill empty array, used for print processing
						Fill();						//fill frames with zeros for print processing, error reduction
						pointer = 0;				//used to add array at beginning of frame start
						LRU = 0;
						current = fileRead.nextInt();
						ids.add(" ");
						ids.add(" ");
					}
					ids.add(current);
					if(!checkFilled()) {
						faults++;
						frames[pointer] = new Entry(current,size); //0-size of current frameMax is the range to compute LRU 0 indicates least
						computeLRU(current);					//compute LRU based on a status bit in object
						pointer++;
						clone = new Entry[clone.length];		//have to clone entries since referencing the same object later
						for(int i = 0; i < clone.length; i++) {
							clone[i] = new Entry(frames[i].getid(),frames[i].getStatus());
						}
						output.add(clone);
					} else if(!checkValue(current)) {			//if current value isnt in the frames then replace current LFU and calc new LFU 
						faults++;
						frames[LRU] = new Entry(current,size);
						computeLRU(current);
						clone = new Entry[clone.length];
						for(int i = 0; i < clone.length; i++) {
							clone[i] = new Entry(frames[i].getid(),frames[i].getStatus());
						}
						output.add(clone);
					} else {	
						recalcLRU(current);			//recalc LRU just incase value just accesed
						output.add(empty.clone()); //input empty list if values is in frames already
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			print();
		
	}	
	private void print() {
		System.out.println("Least Recently Used Algortithm");
		System.out.println("---------------------------------------------------------------------------------------------------");
		for(int i = 0; i < ids.size(); i++) {
			if(ids.get(i) != " ") {
			System.out.printf("%-2s",ids.get(i));
			}
			else System.out.printf("%-2s"," ");
		}
		System.out.println();
		System.out.println("---------------------------------------------------------------------------------------------------");
		for(int i =0; i < maxFrames; i++) {				//maxframes at any given moment in array list
			for(int j = 0; j < output.size(); j++) {
				if(output.get(j).length > i) {
					if(output.get(j)[i].getid() == -1 ) { //print id values give
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
			empty[i] = new Entry(-1,-1);	//put -1 as indicator for blanks when printing
		}
		
	}
	
	private void Fill() {
		for(int i = 0; i < frames.length; i++) {
			frames[i] = new Entry(0,0);		//initiliaze to prevent null pointers when checking
		}
		
	}

	private boolean checkValue(int a) {
		boolean present = false;
		for(int i = 0; i < frames.length; i++) {
			if(frames[i].getid() == a) {  //if value is in frames 
				present = true;
			}
		}
		return present;
	}
	
	private boolean checkFilled() {
		return (pointer > frames.length-1?true:false); //used to make sure frames are filled before using LFU
	
	}
	
	private void recalcLRU(int p) {
		int max = 0,maxId =0;
		int[] posHold = new int[frames.length];
		boolean in = false;
		posHold[posHold.length-1] = p;
		for(int i = posHold.length-2; i >=0;i--) {
			max = 0;
			in = false;
			for(int k = 0; k < frames.length;k++) {
				if(frames[k].getid() != p && frames[k].getStatus() >= max ) {
					for(int j = 0; j < posHold.length;j++) {
						if(frames[k].getid() == posHold[j]) {
							in = true;
						}
					} 
					if(!in) {
						max = frames[k].getStatus();
						maxId=frames[k].getid();posHold[i] = maxId;
					}
				} 	
			}		
		}
		for(int i = 0; i < posHold.length; i ++) {
			if(frames[i].getid() == posHold[0]) {
					LRU = i;
					
			}
			for(int k = 0; k < frames.length; k ++ ) {
				if(frames[k].getid() == posHold[i]) {
					frames[k].setStatus(i);
				}
				
			}
			
		}
	}
	private void computeLRU(int used) {
		int newStat = 0;
		for(int i = 0; i < frames.length; i++) {
			if(frames[i].getid() != used && frames[i].getid() != 0){
				newStat = frames[i].getStatus()-1;
				frames[i].setStatus(newStat);
				if(frames[i].getStatus() == 0) {
					LRU = i;
				}
			} 
		}
	}
}
