package searchEngine;

import java.util.Arrays;

public class EditDistance {	 
	private static int calDist(String word1, String word2, int[][] mem, int i, int j){ 
	    if(i<0){
	        return j+1;
	    }else if(j<0){
	        return i+1;
	    }
	 
	    if(mem[i][j]!=-1){
	        return mem[i][j];
	    }
	 
	    if(word1.charAt(i)==word2.charAt(j)){
	        mem[i][j]=calDist(word1, word2, mem, i-1, j-1);
	    }else{
	        int prevMin = Math.min(calDist(word1, word2, mem, i, j-1), calDist(word1, word2, mem, i-1, j));
	        prevMin = Math.min(prevMin, calDist(word1, word2, mem, i-1, j-1));
	        mem[i][j]=1+prevMin;
	    }
	 
	    return mem[i][j];    
	}
	
	public static int minDist(String word1, String word2) {
	    int a=word1.length();
	    int b=word2.length();
	    int[][] mem = new int[a][b];
	    for(int[] arr: mem){
	        Arrays.fill(arr, -1);
	    }
	    return calDist(word1.toLowerCase(), word2.toLowerCase(), mem, a-1, b-1);
	}
}
