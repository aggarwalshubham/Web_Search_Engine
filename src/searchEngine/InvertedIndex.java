package searchEngine;

/*
 * InvertedIndex - Given a set of text files, implement a program to create an 
 * inverted index. Also create a user interface to do a search using that inverted 
 * index which returns a list of files that contain the query term / terms.
 * The search index can be in memory. 
 */
import java.io.*;
import java.util.*;

public class InvertedIndex{
	public static String directoryPath = "WebPages/text";
    Map<Integer,String> sources;
    HashMap<String, HashSet<Integer>> index;

    InvertedIndex(){
        sources = new HashMap<Integer,String>();
        index = new HashMap<String, HashSet<Integer>>();
    }

    public void buildIndex(String[] files){
        int i = 0;
        for(String fileName:files){            
            try(BufferedReader file = new BufferedReader(new FileReader(directoryPath+"/"+fileName))){
                sources.put(i,fileName);
                String ln;
                while( (ln = file.readLine()) !=null) {
                    String[] words = ln.split("\\W+");
                    for(String word:words){
                        word = word.toLowerCase();
                        if (!index.containsKey(word))
                            index.put(word, new HashSet<Integer>());
                        index.get(word).add(i);
                    }
                }
            } catch (IOException e){
                System.out.println("File "+fileName+" not found. Skip it");
            }
            i++;
        }
        
    }

    public ArrayList<String> find(String phrase){
    	ArrayList<String> fileNames;
    	try {
    		fileNames = new ArrayList<String>();
	        String[] words = phrase.split("\\W+");
	        String hashKey = words[0].toLowerCase();
	        if(index.get(hashKey) == null) {
	        	 System.out.println("Not found *_*");
	        	return null;
	        }
	        HashSet<Integer> res = new HashSet<Integer>(index.get(hashKey));
	        for(String word: words){
	            res.retainAll(index.get(word));
	        }
	
	        if(res.size()==0) {
	            System.out.println("Not found!!!");
	            return null;
	        }
	        for(int num : res){
	        	fileNames.add(sources.get(num));
	        }
    	}catch(Exception e) {
    		 System.out.println("Not found");
    		 System.out.println("Exception Found:" + e.getMessage());
    		 return null;
    	}  
    return fileNames;
    }

	public static void main(String args[]){
		
		File testDirectory = new File(directoryPath);
		String[] testingFiles = testDirectory.list();
		ArrayList<String> fileNames = new ArrayList<String>();
		InvertedIndex index = new InvertedIndex();
        
        index.buildIndex(testingFiles);

        System.out.println("Print search phrase: ");
        Scanner input = new Scanner(System.in);
        String phrase = input.next();
        fileNames =  index.find(phrase);
        
        Iterator<String> itr = fileNames.iterator();
		while(itr.hasNext()) {
			System.out.print(itr.next()+" ");
		}
		
        input.close();
	}
}