package searchEngine;

import java.io.IOException;
import java.util.Scanner;

public class Menu {

	public Menu() {
		// TODO Auto-generated constructor stub
	}
	
	public static void showMenuOptions() {
		String menuOptions = "" +
		 "\nSearch Engine based on Java Tutorials" + 
		 "\n--------------------------------------" + 
		 "\n1 - Download Database." + 
		 "\n2 - Search Keywords using String matching"  + 
		 "\n3 - Search URLs using Regular Expression" + 
		 "\n4 - Frequency of all words from each File in Database" + 
		 "\n0 - Exit" + 
		 "\nSelect an Option from Menu: ";

		StdOut.println(menuOptions);		
	}
	
	public static void getUsersOption(String directoryPath) {
		
		boolean displayMenu = true;
		Scanner input = new Scanner(System.in);
		
		while(displayMenu) {
			
			showMenuOptions();
	        
		    try {
		    	
		        int option = input.nextInt();  
		        switch (option) {
		        case 1:  
		        		Spider.GetData();
		                break;
		        case 2: 
		        		Helper.searchWordInDatabase(directoryPath);
		                break;
		        case 3: 
		        		Helper.findURLsInDirectory(directoryPath);
	        			break;
		        case 4:
		        		Helper.getWordsFreqInAllFiles(directoryPath);
		        		break;
		        case 0:
		        		displayMenu = false;
		        		System.out.println("Exiting Program!");
		        		System.exit(0);
		        		break;
		        default:
			        	System.out.print("The entered value is unrecognized!\n\n");
			        	break;
		       }
		    } catch (Exception e) { 
		    	StdOut.println("Enter input in correct format");
		    	input.next();
		    }
	    }
	}

}
