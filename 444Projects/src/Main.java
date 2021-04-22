
/* Gregory Thornton
 * April 11, 2019 COSC-444 Winter 2019
 * 
 * This is the main Driver class that the menu to
 * run all Files is started from.
 * Turing Machine file will be parsed then ran inside of
 * Menu.
 */
import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner keyb = new Scanner(System.in), fileScan;
		File file; // Input file to be used
		String input = null; // Hold whatever in that needs to be parsed
		char push, move; // holds parsed chars
		int fileNum = -1; // Selector for different files
		// first array is the state
		// 0 = q0
		// 1 = q1
		// 2 = q2
		// etc
		// second array for input on tape
		// 0 = 0
		// 1 = 1
		// 2 = B
		// 3 = #
		// IE stateInfo[1][0] = state 1 input 0
		int stateMap[][];
		// first array is state
		// second is current input on tape
		// returns what to push on to stack
		char pushMap[][];
		// first array is state
		// second is current input on tape
		// returns which way to move
		char moveMap[][];
		int[] finalStates;
		char[] tape = new char[1000];
		for (int i = 0; i < tape.length; i++) {
			tape[i] = 'B';
		}

		int current, startState, input2, state, tapeInput, nextState, numStates, head, nextMove;
		boolean accept = false;

		System.out.print("Enter finite state acceptor file:");
		input = keyb.next();

		// Just in case improper file is input at command line
		while (fileNum < 0 || fileNum > 5) {
			try {
				// Parse Input and cast to Integer for switch
				fileNum = Integer.parseInt(String.valueOf(input.charAt(input.length() - 1)));
			} catch (NumberFormatException e) {
				System.out.print("Input only numbers between 1 and 5 inclusive:");
				input = keyb.next();
			}
		}

		try {
			// Get referenced File
			file = new File(System.getProperty("user.dir") + "/src/" + input);

			// Pointer Scanner to file
			fileScan = new Scanner(file);

			// Initialize Maps to correct num of states
			numStates = fileScan.nextInt();
			stateMap = new int[numStates][4];
			pushMap = new char[numStates][4];
			moveMap = new char[numStates][4];

			// Move Scanner to next important line
			for (int i = 0; i <= 5; i++) {
				fileScan.nextLine();
			}

			// Parse Start State
			input = fileScan.next();
			startState = Integer.parseInt(String.valueOf(input.charAt(input.length() - 1)));
			fileScan.nextLine();

			// Initialize final states holder
			// Each state is a index in array
			finalStates = new int[fileScan.nextInt()];
			fileScan.nextLine();

			// Add final state(s)
			for (int i = 0; i < finalStates.length; i++) {
				input = fileScan.next();
				finalStates[i] = (input.length() < 3 ? Integer.parseInt(String.valueOf(input.charAt(1)))
						: Integer.parseInt(input.substring(1, input.length())));
			}

			// Get How many rules
			fileScan.nextLine();
			input2 = fileScan.nextInt();
			fileScan.nextLine();

			for (int i = 0; i < input2; i++) {
				// Parse Each Rule Convert chars to numbers to store as Coordinates in Map
				input = fileScan.next();
				state = (input.length() < 3 ? Integer.parseInt(String.valueOf(input.charAt(1)))
						: Integer.parseInt(input.substring(1, input.length())));
				input = fileScan.next();
				tapeInput = (input.compareTo("0") == 0 ? 0
						: (input.compareTo("1") == 0 ? 1 : (input.compareTo("B") == 0 ? 2 : 3)));
				input = fileScan.next();
				nextState = (input.length() < 3 ? Integer.parseInt(String.valueOf(input.charAt(1)))
						: Integer.parseInt(input.substring(1, input.length())));
				input = fileScan.next();
				push = input.charAt(0);
				input = fileScan.next();
				move = input.charAt(0);

				// Input Coordinates for next State
				// Input Coordinates for next String to push on stack
				stateMap[state][tapeInput] = nextState;
				pushMap[state][tapeInput] = push;
				moveMap[state][tapeInput] = move;

				// System.out.println(state+" "+tapeInput+" "+nextState+" "+push+" "+move);
			}

			System.out.print("Enter input String:");
			input = keyb.next();

			// Run menu as long as quit is not input regardless of case
			while (input.compareTo("quit") != 0) {
				// always start at 250
				// make startstate the first state
				head = 250;
				current = startState;

				// put input on tape
				for (int i = 0; i < input.length(); i++) {
					tape[i + 250] = input.charAt(i);
				}

				// Set nextMovement and head to starting positions for
				// beginning of loop
				nextState = current;
				nextMove = head;
				while (accept == false) {
					// Update old references
					current = nextState;
					head = nextMove;

					// Translate tapeInput
					tapeInput = (tape[head] == '0' ? 0 : (tape[head] == '1' ? 1 : (tape[head] == 'B' ? 2 : 3)));

					// save Next Move
					nextMove += (moveMap[current][tapeInput] == 'R' ? 1 : -1);

					// save next State
					nextState = stateMap[current][tapeInput];

					// push new value onto tape
					tape[head] = pushMap[current][tapeInput];

					// Evaluate if Current reference is at final state
					for (int states : finalStates) {
						if (nextState == states) {
							accept = true;
							break;
						}
					}
				}
				System.out.print("Output string:");

				// Print for files other than 5 since 5 prints a char
				// as an answer
				if (file.getName().compareTo("file5") != 0) {
					for (int i = 0; i < tape.length; i++) {
						if (tape[i] != 'B' && tape[i] != '#')
							System.out.print(tape[i]);
					}
				} else {
					for (int i = 0; i < tape.length; i++) {
						if (tape[i] == 'E' || tape[i] == 'G' || tape[i] == 'L')
							System.out.print(tape[i]);
					}
				}
				System.out.println();
				System.out.print("Enter input String:");
				input = keyb.next();

				// Reset acceptance
				accept = false;
				// Reset Tape
				for (int i = 0; i < tape.length; i++) {
					tape[i] = 'B';
				}
			}
			System.out.println("Program terminated");
			// Catch any Errors, Clean Exit
		} catch (Exception e) {
			System.out.println("Error in program");
			System.out.println(e.getLocalizedMessage());
		}

		keyb.close();

	}
}
