import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day3_2020 {

	public static class Pair{
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		private int x, y;
		
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
			}
		
		public int getX() { return this.x;}
		public int getY() { return this.y;}
		
		}
	
	public static void main(String[] args) {
		int x = 0, y = 0, treeFound = 0,MAX_X = 0, MAX_Y = 0;
		Scanner fileReader = null;
		String line = "";
		Map<Pair, Character> map = new HashMap<Pair, Character>();
		
		try {
			fileReader = new Scanner(new File("Inputs/Day3Map"));
		} catch(FileNotFoundException e) {
			System.out.println("No File Mayne");
		}

		
		while(fileReader.hasNext()) {
			line = fileReader.next();
			for(char loc : line.toCharArray()) {
				map.put(new Pair(x,y), loc);
			//	System.out.print("    " + x + "-" + y);
				x++;
				
			}
			if(x > MAX_X)
				MAX_X = x;
			//System.out.println();
			x = 0;
			y++;
		}
		MAX_Y = y;
		
		//ap.keySet().forEach(f -> System.out.println((f.getX() == 0 ? f.getX() : "")+" "  + f.getY()));
		x = 0;
		y = 0;
		
		while(map.get(new Pair(x,y)) != null) {
			if(map.get(new Pair(x,y)) == '#') {
				treeFound++;
			}
			x = (x + 3)%MAX_X;
			y++;
		}
		
//		for(int l = 0; l <= MAX_X; l++) {
//			System.out.print(l % MAX_X + " ");
//		}
//		System.out.println();
//		System.out.println(MAX_X);
		System.out.println(treeFound);
		
		Pair[] slopes = {new Pair(1,1), new Pair(3,1),new Pair(5,1), new Pair(7,1), new Pair(1,2)};
		int[] treesFound = new int[slopes.length];
		for(int t : treesFound) { t = 0;}
		for(int s = 0; s < slopes.length; s++) {
			x = 0;
			y = 0;
			while(map.get(new Pair(x,y)) != null) {
				if(map.get(new Pair(x,y)) == '#') {
					treesFound[s]++;
				}
				x = (x + slopes[s].getX())%MAX_X;
				y+=slopes[s].getY();
			}
		}
		
		long Final_treeFound = 1;
		for(int t : treesFound) {
			Final_treeFound *=t;
		}
		System.out.println(Final_treeFound );
		
	}
}
 