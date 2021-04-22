import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day5_2020 {

	public static void main(String[] args) {
		// System.out.println(64/2);
		boolean[][] seats = new boolean[128][7];
		boolean[] filled = new boolean[874];
		int low_row = 0, high_row = 127, low_column = 0, high_column = 7, highest_seat = 0;
		Scanner fileReader = null;
		for(boolean[] r : seats) {for(boolean c : r) {c = false;} }
		for(boolean b : filled) {b = false;}
		
		System.out.println(seats.length);
		try {
			fileReader = new Scanner(new File("Inputs/Day5Passes"));
		} catch (FileNotFoundException e) {
			System.out.println("No File Mayne");
		}

		while (fileReader.hasNext()) {

			String pass = fileReader.nextLine();
			for (int i = 0; i < pass.length(); i++) {
				if (pass.charAt(i) == 'F') {
					high_row = (high_row + low_row) / 2;
				} else if (pass.charAt(i) == 'B') {
					low_row = high_row - (high_row - low_row) / 2;
				} else if (pass.charAt(i) == 'L') {
					high_column = (high_column + low_column) / 2;
				} else if (pass.charAt(i) == 'R') {
					low_column = high_column - (high_column - low_column) / 2;
				}
			}
			System.out.println(low_row + " " + high_row);
			System.out.println(low_column + " " + high_column);
			System.out.println();
			if ((high_row * 8 + high_column) > highest_seat)
				highest_seat = high_row * 8 + high_column;
			System.out.println(highest_seat);
			try {
				seats[high_row][high_column] = true;
			} catch (ArrayIndexOutOfBoundsException e) {
				
			}
			filled[highest_seat-1] = true;
			low_row = 0;
			high_row = 127;
			low_column = 0;
			high_column = 7;
		}
		for(int i = 0; i < seats.length; i++) {
			for(int j = 0; j < seats[i].length; j++) {
				int id = (i * 8 + j);
						if(!seats[i][j]) {
;				System.out.println(i + " " + j + " " +  seats[i][j] + " " + id);}
				}
		}
		System.out.println();
//		for(int i = 0; i < filled.length; i++) {System.out.println(i + " " + filled[i]);}
	}
}
