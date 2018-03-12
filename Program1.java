import java.util.Scanner;

public class Program1 {

	public static void main(String[] args) {
			Scanner keyb = null;
			keyb = new Scanner(System.in);
			int choice = 0;       
			String d = " ";
			
			int [] codehold = new int [8];
			
			System.out.println("Press 1 to convert a decimal number to a binary number.");
			System.out.println("Press 2 to convert from binary to decimal");
			System.out.println("Press 3 to end the program");
			 
			choice = keyb.nextInt();
			while(choice>0 && choice<4)
			{int dec=0;
				switch(choice)
				{
				case 1: System.out.println("Enter your unsigned decimal number");
				dec = keyb.nextInt();
				for( int j =7;j>=0;j--)
					{
					
					if(dec<(int)Math.pow(2,j)){
					codehold[j]=0;
					}
					else{
					codehold[j]=1;
					dec=(int) (dec-Math.pow(2, j));
					}
					}
				System.out.println("The Binary equivalent is:");
					for(int j = 7;j>=0;j--)
					{
						System.out.print(codehold[j]);
						
					}
					System.out.println();
					
					System.out.println("Press 1 to convert a decimal number to a binary number.");
					System.out.println("Press 2 to convert from binary to decimal");
					System.out.println("Press  3 to end the program");
					choice = keyb.nextInt();
					 break;
					 
				case 2: System.out.println("enter your 8 bit binary code");
						d = keyb.next();
						
						for(int j =0 ,i =7; j<8;j++)
						{ 
							if(d.charAt(i)-48==1)
							{	
								dec=(int) (dec+Math.pow(2.0, j));
								i--;
							}	
							else{
							i--;
							}
						}
						System.out.println(dec);
						System.out.println("Press 1 to convert a decimal number to a binary number.");
						System.out.println("Press 2 to convert from binary to decimal");
						System.out.println("Press  3 to end the program");
						choice = keyb.nextInt();
					break;
					
				case 3: System.out.println("The program is ending");
				System.exit(0);
				break;
				}
			}	
keyb.close();
	}

}
