/**
 * @author Ishpreet Singh
 * @author Harnoor Singh
 * */
package searchEngine;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;

public class Helper {

	public static ArrayList<Integer> occurences = new ArrayList<Integer>();
	public static Scanner input = new Scanner(System.in);
	public Helper() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Method to Remove Special symbols from start and end of string
	 * @params words -> Array of words
	 * */
	static String [] CleanWords(String [] words) {
		List<String> ls = new ArrayList<String>();
		for(String word : words) {
			
			String newWord = word;
			if(newWord.length() > 0) {
				char ch = newWord.charAt(newWord.length() - 1);
				while(newWord.length() > 0 && !Character.isLetterOrDigit(ch)) {
					newWord = newWord.substring(0, newWord.length() - 1);
					if(newWord.length() > 0) {						
						ch = newWord.charAt(newWord.length() - 1);
					}
				}
				
				if(newWord.length() > 0) {						
					ch = newWord.charAt(0);
				}
				while(newWord.length() > 0 && !Character.isLetterOrDigit(ch)) {
					newWord = newWord.substring(1);
					if(newWord.length() > 0) {						
						ch = newWord.charAt(0);
					}
				}
			}
			if(newWord.length() > 0) {				
				ls.add(newWord);
			}
		}
		String [] cleanWords = new String[ls.size()];
		int index = 0;
		for(String word : ls) {
			cleanWords[index++] = word.toLowerCase();
		}
		return cleanWords;
	}
	
	/**
	 * Method to get all the words from a file
	 * @param filePath -> File Path
	 * */
	static String [] GetWordsFromFile(String filePath) {
		In inputFile = new In(filePath);
		String [] words = CleanWords(inputFile.readAllStrings());
		return words;
	}
	
	/**
	 * Method to get frequency of words from a file
	 * @param filePath -> File Path
	 * */
	static Map<String, Integer> GetWordsFrequencyFromFile(String filePath) {
		String [] words = GetWordsFromFile(filePath);
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		for(String word : words) {
			if(map.containsKey(word)) {
				Integer value = map.get(word);
				map.put(word, value + 1);
			} else {
				map.put(word, 1);
			}
		}
		return map;
	}
	
	/**
	 * Method to get all the files present in a directory
	 * @param directoryPath -> Directory Path
	 * */
	static String [] GetFilesFromDirectory(String directoryPath) {
		File fileObj = new File(directoryPath);
		String[] files = fileObj.list();
				
//		For Testing, UnComment this for lesser number of test files
//		files = Arrays.copyOfRange(files, 0, 2);
		return files;
	}
	
	/**
	 * Method to get all words from all the files present in a directory
	 * @param directoryPath -> Directory Path
	 * */
	static String [] GetWordsFromDirectory(String directoryPath) {
		String[] files = GetFilesFromDirectory(directoryPath);
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		for(String file : files) {
			String [] wordsPerFile = GetWordsFromFile(directoryPath + "/" + file);
			for(String word : wordsPerFile) {
				if(map.containsKey(word)) {
					Integer value = map.get(word);
					map.put(word, value + 1);
				} else {
					map.put(word, 1);
				}
			}
		}
		
		Set <String> myKeys = map.keySet();
		String[] wordsInDirectory = myKeys.toArray(new String[myKeys.size()]);
		
		return wordsInDirectory;
	}
	
	
	/**
	 * Method to get frequency of words from all files in a directory
	 * @param directoryPath -> Directory Path
	 * */
	static Map<String, Integer> GetWordsFrequencyFromDirectory(String directoryPath) {
		
		String[] files = GetFilesFromDirectory(directoryPath);
		Map<String, Integer> map = new HashMap<String, Integer>();	
		for(String file : files) {
			String [] wordsPerFile = GetWordsFromFile(directoryPath + "/" + file);
			for(String word : wordsPerFile) {
				if(map.containsKey(word)) {
					Integer value = map.get(word);
					map.put(word, value + 1);
				} else {
					map.put(word, 1);
				}
			}
		}
		return map;	
	}
	
	/**
	 * Method to Demonstrate how to print key and values in a map 
	 * */
	static void printMap(Map<String, Integer> map) {
		map.forEach((key, value) -> {
			StdOut.println("key:" + key + " Value:" + value);
		});
	}
	
	//harry
	/**
	 * Method to get all the words from a file in a string
	 * @param filePath -> File Path
	 * */
	static String getStringFromFile(String filePath) {
		In inputFile = new In(filePath);
		String wordString = inputFile.readAll();
		return wordString;
	}

	/**
	 * Method to find offset of all the keyword present in file 
	 * */
	static ArrayList<Integer> findKeywordsFromFile(String filePath, String keyword) {
		String  wordString = getStringFromFile(filePath);
		//StdOut.println(wordString);
		//StdOut.println(wordString.length());
		ArrayList<Integer> offSetList = new ArrayList<Integer>();
		
		KMP kmp = new KMP();
		offSetList = kmp.KMPSearch(keyword, wordString.toLowerCase()); 
		
		return offSetList;
	}
	
	/**
	 * Method to get all the words from all files in directory in a string
	 * @param filePath -> File Path
	 * */
	private static String getStringFromDirectory(String directoryPath) {
		StringBuilder sb = new StringBuilder();
		String[] files = GetFilesFromDirectory(directoryPath);
		for(String file : files) {
			String wordStringPerFile = getStringFromFile( directoryPath + "/" + file);
			sb.append(wordStringPerFile);
		}
		
		return sb.toString();
	}
	
	/**
	 * Method to find offset of all the keyword present in all files in directory
	 * */
	static ArrayList<Integer> findKewordsFromDirectory(String directoryPath, String keyword) {
		String  wordString = getStringFromDirectory(directoryPath);
		//StdOut.println(wordString);
		//StdOut.println(wordString.length());
		ArrayList<Integer> offSetList = new ArrayList<Integer>();
		int count=0;
		int offset=0;
		int off=0;
		
		KMP kmp = new KMP();
		kmp.KMPSearch(keyword, wordString); 
		
		return offSetList;
	}

	/**
	 * Method to Demonstrate how to print values in a ArrayList 
	 * */
	static void printList(ArrayList<Integer> offSetList) {
		Iterator<Integer> itr = offSetList.iterator();
		while(itr.hasNext()) {
			System.out.print(itr.next()+" ");
		}	
	}

	/**
	 * Method to Rank web pages according to frequency 
	 * */
	static Map<String, Integer> rankingResults(Map<String, Integer> results) {
		Map<String, Integer> sortedResults;
		sortedResults = results.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,  LinkedHashMap::new));
		
		return sortedResults;
	}
	
	/**
	 * Method to print final results on console 
	 * */
	static void printFinalResults(Map<String, Integer> results,String search) {
	
		Map<String, Integer> rankedResults = rankingResults(results); // ranking of web pages using sorting

		Set<String> keys = rankedResults.keySet();
		String[] keysArray = keys.toArray(new String[keys.size()]);
		for(int i=0; i<keysArray.length && i<10;i++) {
			BufferedReader linkReader = null;
			InputStream response = null;
			String link= null;
			try {
				occurences = findKeywordsFromFile(keysArray[i],search.toLowerCase());// pattern matching using KMP  					    		    
				linkReader = new BufferedReader(new FileReader(keysArray[i]));
				link = linkReader .readLine();
				
				response = new URL(link).openStream();
		        Scanner sc = new Scanner(response);
		        String title = null;
		        try {
		        	String responseBody = sc.useDelimiter("\\A").next();
		        	 title = responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>"));
		        }catch (Exception exception) {
		        	title = null;
		        }
		        try {
			        if(title.equals(null)) {
			        	System.out.println("");
			        }
			        else {
			        	System.out.println(title);
			        }
		        }
		        catch (Exception exception) {
		        	
		        }
		        sc.close();
			} 
		    catch (Exception e) {
				e.printStackTrace();
			}
		    StdOut.println(link);
			StdOut.println("Database File Name: "+keysArray[i]);
			StdOut.println("Off set of keyword: "+occurences);
			StdOut.println("Frequency: " +rankedResults.get(keysArray[i]));
			StdOut.println();
		}
	}
	
	/**
	 * Method to Demonstrate find frequency of particular keyword 
	 * */
	static int getKeywordFrequencyFromFile(String filePath,String keyword) {
		String [] words = GetWordsFromFile(filePath);
		int count = 0; 
        for (int i=0; i < words.length; i++) {
        	if (keyword.equals(words[i])) {  
        		count++;
        	}
        }
        return count;
	}
	
	/**
	 * Method to Demonstrate find phone numbers using regex 
	 * */
	public static void findPhoneNumbers(String text) {
		String pattern = "(\\()?(\\d){3}(\\))?[- ](\\d){3}-(\\d){4}";
		Pattern r = Pattern.compile(pattern);
		 Matcher m = r.matcher(text);
	      while (m.find( )) {
	          System.out.println("Phone Number: " + m.group(0) + " found at " + m.start(0));
	      } 
	}
	
	/**
	 * Method to Demonstrate find URL using regex 
	 * */
	public static void findURL(String text) {
		String pattern = "(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?";
		Pattern r = Pattern.compile(pattern);
		ArrayList<String> urls = new ArrayList<String>();
		Matcher m = r.matcher(text);
	    while (m.find( )) {
	        System.out.println("URL: " + m.group(0));
        } 
	}
	
	static void findURLsInFile(String filePath) {
		In inputFile = new In(filePath);
		ArrayList<String> urls = null;
		String textFromFile = inputFile.readAll();
		findURL(textFromFile);
	}
	
	static void findURLsInDirectory(String directoryPath) {	
		String [] files = GetFilesFromDirectory(directoryPath);
		ArrayList<String> urls = null;
		for(String file : files) {
			String filePath = directoryPath + "/" + file;
			findURLsInFile(filePath);
		}
		
	}
	
	public static boolean searchWordInDatabase(String directoryPath) {
		boolean wordFound = true;
		File testDirectory = new File(directoryPath);
		String[] files = testDirectory.list();	        		
		System.out.println("Enter what you want to search: ");
		String search = input.next();
		ArrayList<String> fileNames = new ArrayList<String>();
		InvertedIndex index = new InvertedIndex();	        	        
	    index.buildIndex(files);	
	    
	    Boolean spellCheck= SpellCheck.checkSpelling(search); // spell check using edit distance
		Map<String, Integer> rankingMap = null;
		while(!spellCheck) {
			System.out.println("Enter what you want to search again: ");
			search = input.next();
    		spellCheck = SpellCheck.checkSpelling(search); // spell check using edit distance
		}
		
		fileNames = index.find(search); //record-level inverted index- Gives file names of the only those files where keyword is present.
	    
	    if(fileNames == null) {
	    	wordFound = false;
	    	return wordFound;
	    }
	    String[] testingFiles = fileNames.toArray(new String[fileNames.size()]);  
	    if(testingFiles.length==0) {
	    	System.out.println("Not Present!!");
	    	wordFound = false;
	    	return wordFound;
	    }
		
		if(spellCheck) {
    		rankingMap = new HashMap<String, Integer>();
        	for(int i=0;i<testingFiles.length;i++){
        		Map<String, Integer> map1 = GetWordsFrequencyFromFile(directoryPath + "/" + testingFiles[i]); //getting frequency using hashmap		        		
    			Integer frequency = map1.get(search); //getting frequency using hashmap		        		
    			if(frequency!=null)
    				rankingMap.put(directoryPath + "/" + testingFiles[i], frequency);
    		}
        	printFinalResults(rankingMap,search); // print top 10 results after ranking 
		}
		return wordFound;
	}
	
	public static void getWordsFreqInAllFiles(String directoryPath) {
		StdOut.println("Frequency of each words from each file");
		File testDirectory3 = new File(directoryPath);
		String[] testingFiles3 = testDirectory3.list();
		
//		int fileLength = Math.min(testingFiles3.length, 5);
		
		int fileLength = testingFiles3.length;
		
		for(int i=0;i<fileLength;i++){
			System.out.println(testingFiles3[i]);
    		Map<String, Integer> map3 = GetWordsFrequencyFromFile(directoryPath + "/" + testingFiles3[i]);
    		printMap(map3);
		}
	}
	
	public static void main(String[] args) {
		String directoryPath = "WebPages/testingData";
		String filePath = "WebPages/testingData/GeekForGeeks120.txt";
		
		StdOut.println("Frequency of all words");
		Map<String, Integer> map = GetWordsFrequencyFromDirectory(directoryPath);
		Helper.printMap(map);
		
		
		String [] arr = GetWordsFromFile(filePath);
		for(String element : arr) {
			StdOut.println(element);
		}
		
//		How to read from map
		Map<String, Integer> map3 = GetWordsFrequencyFromDirectory(directoryPath);		
//		Printing the map
		printMap(map3);

//		How to read from map
		File testDirectory = new File(directoryPath);
		String[] testingFiles = testDirectory.list();
		for(int i=0;i<testingFiles.length;i++)
		{
			Map<String, Integer> map4 = GetWordsFrequencyFromFile(directoryPath + "/" + testingFiles[i]);	
			//Printing the map
			printMap(map4);
		}

		
		ArrayList<Integer> offSetList1 = new ArrayList<Integer>();
		String keyword = "Java";
		
		StdOut.println(keyword+" is present at following locations: ");
		offSetList1 = findKewordsFromDirectory(directoryPath,keyword);
		printList(offSetList1);
		
		findURLsInDirectory(directoryPath);

		
			
	}
}
