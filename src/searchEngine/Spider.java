package searchEngine;

import java.io.IOException;

public class Spider {

	public Spider() {
		// TODO Auto-generated constructor stub
	}
	
	public static void GetData() throws IOException {
		
		String[] startURLs = new String[] {
				"https://www.javatpoint.com/java-tutorial", 
				"https://www.tutorialspoint.com/java/index.htm",
				"https://docs.oracle.com/javase/tutorial/",
				"https://www.guru99.com/java-tutorial.html",
				"https://www.geeksforgeeks.org/java/",
				"https://beginnersbook.com/java-tutorial-for-beginners-with-examples/"
		};
		
		String[] siteName = new String[] {"JavaTPoint", "TutorialsPoint", "Oracle", "Guru99", "GeekForGeeks", "Beginnersbook"};
		
		
		for(int i=0;i<startURLs.length;i++)
		{
			Crawler crawler = new Crawler(startURLs[i],siteName[i]);
			crawler.start();
		}
	}

}
