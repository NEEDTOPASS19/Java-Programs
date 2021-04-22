import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day6_2020 {

	public static void main(String[] args) {
		boolean allEqual = true;
		int total = 0, numberofpeople = 0;
		Scanner fileReader = null;
		String line = "";
		
		ArrayList<Character> yes = new ArrayList<>();
		ArrayList<Integer> numberAnswered = new ArrayList<>();
		
		try {
			fileReader = new Scanner(new File("Inputs/Day6QuestionResponses"));
		} catch(FileNotFoundException e) {
			System.out.println("No File Mayne");
		}

		
		do {
			line = fileReader.nextLine();
			
			if(line.length() == 0) {
				for(int selectedAnswer : numberAnswered) {
					
					if(selectedAnswer != numberofpeople) {
						System.out.println("Not Equal: " + numberofpeople + " And " + selectedAnswer);
						allEqual = false;
						break;
					}
				}
				
				if(allEqual)
					total+=numberofpeople;
				
				yes.clear();
				numberAnswered.clear();
				allEqual = true;
				numberofpeople = 0;
				
			} else {
			
			for(char answer : line.toCharArray()) {
				if(!yes.contains(answer)) { 
					yes.add(answer);
					numberAnswered.add(1);
				} else {
					numberAnswered.set(yes.indexOf(answer), numberAnswered.get(yes.indexOf(answer))+1);
				}
			}
			
//			for(int selectedAnswer : numberAnswered) {
//				System.out.print(selectedAnswer + "--" + " ");
//			}
			
			numberofpeople++;
			}
		}while(fileReader.hasNext());
		total+=yes.size();
		System.out.println(total);
		
	}
}
