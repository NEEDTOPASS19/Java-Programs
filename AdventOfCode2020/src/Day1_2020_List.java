import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Day1_2020_List {

	public static void main(String[] args) {
		
		File list = new File("Day1List");
		Scanner fileRead = null;
		int key ,currentIndex, firstNum = -1, secondNum = -1; 
		boolean found = false;
		
		try {
			fileRead = new Scanner(list);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No File Mayne");
		}
		
		ArrayList<Integer> array = new ArrayList<>();
		if(fileRead != null) {
			while(fileRead.hasNextInt()) {
				array.add(fileRead.nextInt());
			}
		}
		
		for(int i = 1; i< array.size(); i++) {
			key = array.get(i);
			currentIndex = i - 1;
			while(currentIndex >= 0 && array.get(currentIndex) > key) {
				array.set(currentIndex + 1, array.get(currentIndex));
				currentIndex--;
			}
			array.set(currentIndex+1, key);
			
		}
		
		array.forEach((n) -> System.out.println(n));
		
		//2 numbers when added = 2020
		for(int i = 0; i < array.size(); i++) {
			firstNum = array.get(i);
			for(int j = array.size() - 1; j >= 0; j--) {
				secondNum = array.get(j);
				if((firstNum + secondNum) == 2020) {
					found = true;
					break;
				} else if((firstNum + secondNum) < 2020) {
					break;
				}
			}
			if(found)
				break;
		}
		
		System.out.println(found);
		System.out.println(firstNum + " + " + secondNum + " = " + firstNum*secondNum);
		
		//3 numbers when added = 2020
		int thirdNum = -1;
		found = false;
		
		for(int i = 0; i < array.size(); i++) {
			firstNum = array.get(i);
			for(int j = array.size() - 1; j >= 0; j--) {
				secondNum = array.get(j);
				for(int k = array.size()/2; k > i; k--){
					thirdNum = array.get(k);
					if((firstNum + secondNum + thirdNum) == 2020) {
						found = true;
						break;
					} else if((firstNum + secondNum+ thirdNum) < 2020) {
						break;
					}
				}
				if(found)
					break;
			}
			if(found)
				break;
		}
		
		System.out.println(found);
		System.out.println(firstNum + " + " + secondNum + " + " + thirdNum + " = " + firstNum*secondNum*thirdNum);
	}

}
