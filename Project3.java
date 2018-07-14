import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
@SuppressWarnings("unchecked")
public class Project3
{
	public static void main(String[] args)
	{
		String[] cityNumber = new String[20];
		String[] cityCode = new String[20];
		String[] cityName = new String[20];
		String[] cityInfo = new String[20];
		String[] cityInfo2 = new String[20];
	
		Digraph graph = new Digraph();
		
		try (BufferedReader br = new BufferedReader(new FileReader("city.dat")))
        {
			int index = 0;
			int splitNum = 0;
			String line;
			
			while ((line = br.readLine()) != null) {
				String trimmed = line.trim();
				String[] info = trimmed.split("[ ]{2,}");
				if (info.length == 6) {
					cityNumber[index] = info[0];
					cityCode[index] = info[1];
					cityName[index] = info[2] + " " + info[3];
					cityInfo[index] = info[4];
					cityInfo2[index] = info[5];
				}
				else {
					cityNumber[index] = info[0];
					cityCode[index] = info[1];
					cityName[index] = info[2];
					cityInfo[index] = info[3];
					cityInfo2[index] = info[4];
				}
				index++;				
			}
			
        }
		catch (IOException e) {
			System.out.println("File not found");
		}
		try (BufferedReader br2 = new BufferedReader(new FileReader("road.dat"))) {
			int index2 = 0;
			String line2;
			
			while ((line2 = br2.readLine()) != null) {
				String trimmed = line2.trim();
				String[] info2 = trimmed.split("[ ]{2,}");
				int distance = Integer.valueOf(info2[2]);
				graph.add(info2[0], info2[1], distance);
				index2++;				
			}
		}
		catch (IOException e) {
			System.out.println("File not found");
		}
	    Scanner kb = new Scanner(System.in);
	    String cmd = "z";
	    while (cmd.charAt(0) != 'E') {
	        System.out.println("Command? ");
	        cmd = kb.nextLine().toUpperCase();
	        System.out.println(cmd);
	  			
	        switch(cmd.charAt(0)) { 
	          case 'Q' :
	        	  System.out.println("City code: ");
				  String qLine = kb.nextLine();
	        	  for (int i = 0; i<20; i++) {
	        		  if (qLine.equals(cityCode[i])) {
	        			  System.out.println(cityNumber[i] +" "+ cityCode[i] +" "+ cityName[i]+" " + cityInfo[i]+" " + cityInfo2[i]);
	        			  break;
	        		  }
	        	  }
	        	  break;
	          case 'D' :
	        	  System.out.println("City code: ");
	        	  String dLine = kb.nextLine();
	        	  String[] d = dLine.split(" ");
				  String[] a = new String[2];
				  for (int i = 0; i<20; i++) {
					if (d[0].equals(cityCode[i]))
						a[0] = cityNumber[i];
					if (d[1].equals(cityCode[i]))
						a[1] = cityNumber[i];
				  }
	        	  List<String> path = graph.getPath(a[0], a[1]);
				  System.out.println("Starting at City #" + a[0]);
				  for (int i = 1; i<path.size(); i++) {
					System.out.printf(" to City #");	
					String each = path.get(i);
					System.out.printf(each);
				  }
				  if (path.size() == 1) {
					System.out.println("Total distance to City #" + a[1] + " is " + path.get(0));
				  }

	        	  break;
	          case 'I' :
	        	  System.out.println("City codes(not numbers) and distance: ");
	        	  String iLine = kb.nextLine();
				  boolean ins = false;
				  String[] s = new String[3];
	        	  String[] l = iLine.split(" ");
				  for (int i = 0; i<20; i++) {
					if (l[0].equals(cityCode[i]))
						s[0] = cityNumber[i];
					if (l[1].equals(cityCode[i]))
						s[1] = cityNumber[i];
				  }				  
	        	  int iDistance = Integer.parseInt(l[2]);
	        	  ins = graph.add(s[0], s[1], iDistance);
				  if (ins == false)
					break;
				  System.out.println("You have inserted a road from " + l[0] + " to " + l[1] + " with a distance of " + l[2]);
	        	  break;
	          case 'R' :
				  boolean rem = false;
	        	  System.out.println("City codes(not numbers)");
	        	  String rLine = kb.nextLine();
				  String[] r = new String[3];
	        	  String[] rt = rLine.split(" ");
				  for (int i = 0; i<20; i++) {
					if (rt[0].equals(cityCode[i]))
						r[0] = cityNumber[i];
					if (rt[1].equals(cityCode[i]))
						r[1] = cityNumber[i];
				  }
				  rem = graph.remove(r[0],r[1]);
				  if (rem == false)
					break;
				  System.out.println("You have removed a road from " + rt[0] + " to " + rt[1]);
	        	  break;
	          case 'H' :
	        	   System.out.println("Q   Query the city information by entering the city code.");
	        	   System.out.println("D   Find the minimum distance between two cities.");
	        	   System.out.println("I   Insert a road by entering two city codes and distance.");
	        	   System.out.println("R   Remove an existing road by entering two city codes.");
	        	   System.out.println("H   Display this message.");
	        	   System.out.println("E   Exit.");
	            break;
	          case 'E' :
	            break;
	          default :
	            System.out.println("That is an unrecognisable command :");
	          }
	        System.out.println();
	      }
	      System.out.println("Thank you for using my program!");
	}
}