/*
This programs inputs a file and creates a symbol table using "proc","int",and,"real" as types.
It also will tell what scope variables are 
in at the point in the code a given by the
"LEVEL" column. The test file is located 
in the repository.
*/
import java.io.*;
import java.util.*;

public class Compiler_table {

	public static void main(String[] args) {

		String token;
		String table[][];
		int level = 0, tableNum = 1, lineNum=1;
		Scanner fileread = null;

		table = new String[3][100];
		table[0][0] = "NAME";
		table[1][0] = "TYPE";
		table[2][0] = "LEVEL";

		System.out.println("Enter File name with .txt extension");
		fileread = new Scanner(System.in);
		token = fileread.next();

		try {
			FileInputStream file = new FileInputStream(token);
			fileread = new Scanner(file);

			while (fileread.hasNext() == true) {
				token = fileread.next();
				if(token.contains("\n") == true){
					lineNum++;
				}
				switch (token) {
				case "{":
					level++;
					break;
				case "}":
					if(level>=0){
					level--;}
					break;
				case "proc":
					table[1][tableNum] = token;
					token = fileread.next();
					if(token.charAt(token.length()-1) == ';'){
						table[0][tableNum] = String.valueOf((token.charAt(0)));
					}else{
						table[0][tableNum] = token;
					}
					table[2][tableNum] = String.valueOf(level);
					tableNum++;
					break;
				case "real":
					table[1][tableNum] = token;
					token = fileread.next();
					if(token.charAt(token.length()-1) == ';'){
						table[0][tableNum] = String.valueOf((token.charAt(0)));
					}else{
						table[0][tableNum] = token;
					}
					table[2][tableNum] = String.valueOf(level);
					tableNum++;
					break;
				case "int":
					table[1][tableNum] = token;
					token = fileread.next();
					if(token.charAt(token.length()-1) == ';'){
						table[0][tableNum] = String.valueOf((token.charAt(0)));
					}else{
						table[0][tableNum] = token;
					}
					table[2][tableNum] = String.valueOf(level);
					tableNum++;
					break;
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());

		}
		System.out.printf("%-4s  %-4s   %-4s\n",table[0][0], table[1][0], table[2][0]);
		System.out.println("--------------------------------------------------");
		int i = 1;
		while (i <= tableNum && table[0][i] != null) {
			System.out.printf("%-4s  %-4s   %-4s\n",table[0][i], table[1][i], table[2][i]);
			i++;
		}
		System.out.println(lineNum);
		fileread.close();
	}

}

/*
NAME  TYPE   LEVEL
--------------------------------------------------
main  proc   0   
x     int    1   
y     real   1   
t1    proc   1   
x     int    2   
t2    proc   2   
x     real   3   
y     int    3   
z     int    3   

*/
