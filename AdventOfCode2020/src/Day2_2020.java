import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day2_2020 {

	public static void main(String[] args) {
		File list = new File("Inputs/Day2Input");
		
		Scanner fileRead = null;
		int min, max, numValidPasscodes_Count = 0, numValidPasscodes_Position = 0, timesRuleFound;
		String passcode, stringHold;
		char passcodeRule;
		
		try {
			fileRead = new Scanner(list);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No File Mayne");
		}
		
		if(fileRead != null) {
			int i = 0;
			while(fileRead.hasNextLine()){
				i++;
				timesRuleFound = 0;
				stringHold = fileRead.next();
				min = Integer.parseInt(stringHold.substring(0,stringHold.indexOf("-")));
				max = Integer.parseInt(stringHold.substring(stringHold.indexOf("-")+1, stringHold.length()));
				passcodeRule = fileRead.next().charAt(0);
				passcode = fileRead.next();
				
				for(char rule:passcode.toCharArray()) {
					if(rule == passcodeRule)
						timesRuleFound++;
				}
				
				System.out.println("----------------------");
				System.out.println("Code Number: " + i);
				if(timesRuleFound >= min && timesRuleFound <= max) {
					System.out.println("Valid Count");
					numValidPasscodes_Count++;
				} else {
					System.out.println("Fucked Count");
				}
				
				if(passcode.length() >= max && passcode.length() >= min) {
					if((passcode.charAt(min-1) == passcodeRule && passcode.charAt(max-1) != passcodeRule) || 
					   (passcode.charAt(min-1) != passcodeRule && passcode.charAt(max-1) == passcodeRule)){
						System.out.println("Valid Position");
						numValidPasscodes_Position++;
					} else {
						System.out.println("Fucked Position");
					}
				}
				System.out.println("Found: " + timesRuleFound);
				System.out.println("Min: " + min);
				System.out.println("Max: " + max);
				fileRead.nextLine();

			}
			System.out.println(numValidPasscodes_Count);
			System.out.println(numValidPasscodes_Position);
		}
		
	}

}
