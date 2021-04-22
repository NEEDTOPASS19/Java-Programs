import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day4_2020 {
	static class Passport {

		public String[] passport_info = new String[8];
		
		enum Passport_Parts{
			 byr,
			 iyr,
			 eyr,
			 hgt,
			 hcl,
			 ecl,
			 pid,
			 cid,
		}
		
		public Passport() {
			for(String index : passport_info) {
				index = null; 

				}
			}
		public boolean Validate()  { 
			for(int i = 0; i < passport_info.length - 1; i++) { 
				if(passport_info[i] == null)
					return false;
			}
			return true;
		}
	}

	public static void main(String[] args) {
		int valid = 0;
		Scanner fileReader = null, lineReader = null;
		String line = "", info = "",left = "", right = "";
		Passport hold = new Passport();
		
		try {
			fileReader = new Scanner(new File("Inputs/Day4Passports"));
		} catch(FileNotFoundException e) {
			System.out.println("No File Mayne");
		}

		
		while (fileReader.hasNext()) {
			line = fileReader.nextLine();
			lineReader = new Scanner(line);
			
			if(line.length() == 0) {
				if(hold.Validate()) {
				//System.out.println(hold.Validate());
				for(String index : hold.passport_info) { 
					System.out.println(index); 
					}
					valid++;
				}else{
				}
				System.out.println();
				hold = new Passport();
			}
			while (lineReader.hasNext()) {
				try {
				info = lineReader.next().toLowerCase().trim();
				left = info.substring(0, info.indexOf(":"));
				right = info.substring(info.indexOf(":") + 1);
				
				//System.out.println(right + " " + left);
				switch (Passport.Passport_Parts.valueOf(left)) {
				case byr:
					if(Integer.parseInt(right) >= 1920 || Integer.parseInt(right) <= 2002) {
					hold.passport_info[Passport.Passport_Parts.byr.ordinal()] = right;
					}
					break;
				case iyr:
					if(Integer.parseInt(right) >= 2010 || Integer.parseInt(right) <= 2020) {
					hold.passport_info[Passport.Passport_Parts.iyr.ordinal()] = right;
					}
					break;
				case eyr:
					if(Integer.parseInt(right) >= 2020 || Integer.parseInt(right) <= 2030) {
					hold.passport_info[Passport.Passport_Parts.eyr.ordinal()] = right;
					}
					break;
				case hgt:
					
					if((right.substring(right.length()-2).compareTo("cm") == 0  && right.length() == 5 && (Integer.parseInt(right.substring(0 , 3)) >= 150 || Integer.parseInt(right.substring(0 , 3)) <= 193)) || 
							(right.substring(right.length()-2).compareTo("in") == 0 && right.length() == 4 && (Integer.parseInt(right.substring(0 , 2)) >= 59 || Integer.parseInt(right.substring(0 , 2)) <= 76))) {
					hold.passport_info[Passport.Passport_Parts.hgt.ordinal()] = right;
					}
					break;
				case hcl:
					boolean isA_Z_0_9 = true;
					for(int i = 1; i < right.length(); i++){
						if(!Character.isLetter(right.charAt(i)) && !Character.isDigit(right.charAt(i))) {
							isA_Z_0_9 = false;
						}
					}
//					System.out.println(right.substring(1) + " " + right.substring(1).length()+ "<------------- ");
					if(right.indexOf("#") == 0 && isA_Z_0_9 && right.substring(1).length() == 6) {
						hold.passport_info[Passport.Passport_Parts.hcl.ordinal()] = right;
					}
					break;
				case ecl:if(right.length() == 3 && (right.compareTo("amb") == 0|| 
							right.compareTo("blu") == 0 || 
							right.compareTo("brn") == 0 || 
							right.compareTo("gry") == 0 || 
							right.compareTo("grn") == 0 ||
							right.compareTo("hzl") == 0 || 
							right.compareTo("oth") == 0)) {
					hold.passport_info[Passport.Passport_Parts.ecl.ordinal()] = right;
				}
					
					break;
				case pid: 
					boolean isNum = true;
					for(int i = 0; i < right.length(); i++){
						if(!Character.isDigit(right.charAt(i))) {
							isNum = false;
						}
					}
					if(right.length() == 9 && isNum) {
						hold.passport_info[Passport.Passport_Parts.pid.ordinal()] = right;
				}
					break;
				default:
					break;
				}
				}catch(Exception e) {}
			}
		}
		
		if(hold.Validate())
				valid++;
		System.out.println(valid);
	}

}
