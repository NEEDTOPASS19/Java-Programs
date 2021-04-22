import java.util.*;
import java.io.*;
public class Importer {

	public Importer() {
		
	}
	
	public Scanner fileReader(String fileName) throws FileNotFoundException {
	 
	 Scanner fileReader = null;
	 File file = new File(fileName);
	 fileReader = new Scanner(fileName);
	return fileReader;
	 
	}
	
}
