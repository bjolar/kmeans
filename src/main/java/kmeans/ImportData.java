package kmeans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ImportData {
	
	
	public ArrayList<Word> allwords = new ArrayList<Word>();
	public ArrayList<Blog> allblogs = new ArrayList<Blog>();
	
	/**
	 * 
	 */
	public ImportData() {
	}
	

	
	public Centroids getClusters() {
		readCSV("C:\\Users\\User\\kmeans\\blogdata.txt");
		Centroids centan = new Centroids();
		centan.allblogs = allblogs;
		centan.allwords = allwords;
		centan.initsializeCentroids();
		centan.assignCentroids();
		for (Centroid c : centan.allcentroids) {
			Collections.sort(c.blogList, new Comparator<Blog>() {
			    public int compare(Blog v1, Blog v2) {
			        return v1.getName().compareTo(v2.getName());
			    }
			});
		}
		return centan;
	}
	
	public void test() {
//		double d = pearson(allblogs.get(2), allblogs.get(4));
//		System.out.println(d);
//		System.out.println(allwords.get(10).max);
//		System.out.println(allwords.get(10).min);
		Centroids centan = new Centroids();
		centan.allblogs = allblogs;
		centan.allwords = allwords;
		centan.initsializeCentroids();
		System.out.println(centan.allcentroids.get(1).wordList.get(1).word + centan.allcentroids.get(1).wordList.get(1).count);
		centan.assignCentroids();
		
		//Centroid c = centan.allcentroids.get(0);
		for (Centroid c : centan.allcentroids) {
			System.out.println("---");
			System.out.println(c.id);
			System.out.println("---");
			
			Collections.sort(c.blogList, new Comparator<Blog>() {
			    public int compare(Blog v1, Blog v2) {
			        return v1.getName().compareTo(v2.getName());
			    }
			});
			for (Blog b : c.blogList) {
				System.out.println(b.name);
			}
			System.out.println();
		}
		
	}
	
	/**
	 * Read CSV
	 * @param path
	 */
	public void readCSV(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line;
		    int n = 0;
		    while ((line = br.readLine()) != null) {
		        String[] splitted = line.split("\t");
		        if (n > 0) {
		        	Blog b = new Blog();
		        	b.name = splitted[0];
		        	for(int i = 1; i < splitted.length; i++) {
		        		Word w = new Word();		        		
		        		
		        		w.word = allwords.get(i-1).word;
		        		w.occurance = Integer.parseInt(splitted[i]);
		        		b.wordList.add(w);
		        		if (allwords.get(i-1).min == null ) {
		        			allwords.get(i-1).min = w.occurance;
		        			allwords.get(i-1).max = w.occurance;
		        		} else {
		        			if (allwords.get(i-1).min > w.occurance ) {
		        				allwords.get(i-1).min = w.occurance;
		        			}
		        			if (allwords.get(i-1).max < w.occurance ) {
		        				allwords.get(i-1).max = w.occurance;
		        			}        			
		        		}
//		        		System.out.println(w.word);
//		        		System.out.println(w.occurance);
//		        		System.out.println(b.wordList.get(0).word);
//		        		System.out.println(b.wordList.get(0).occurance);
//		        		System.out.println(b.name);
//			        	System.exit(0);
		        	}
		        	allblogs.add(b);
		        } else {
		        	for(int i = 1; i < splitted.length; i++) {
		        		Word werd = new Word();
		        		werd.word = splitted[i];
		        		allwords.add(werd);
		        	}
		        	//System.out.print(allwords.toString());
		        	//System.exit(0);
		        }
		        n++;
		    }
		} catch (Exception e) {
			System.out.println(e.toString());
	    }
	}
	
	/**
	 * Calculate pearson correlation score
	 * @param first
	 * @param second
	 * @return
	 */
	public double pearson(Blog first, Blog second) {
		double sum1 = 0.0;
		double sum2 = 0.0;
		double sum1sq = 0.0;
		double sum2sq = 0.0;
		double pSum = 0.0;
		int n = 0;
		int size = first.wordList.size();
		for (int i = 0; i < size; i++) {
			Word firstWord = first.wordList.get(i);
			Word secondWord = second.wordList.get(i);
//			System.out.println(firstWord.word);
//			System.out.println(firstWord.word);
//			System.exit(0);
				
			sum1 += firstWord.occurance;
			sum2 += secondWord.occurance;
			sum1sq += Math.pow(firstWord.occurance, 2);
			sum2sq += Math.pow(secondWord.occurance, 2);
			pSum += firstWord.occurance * secondWord.occurance;
			n += 1;
				
		}
		
		if (n == 0) {
			return 0.0;
		}
		double num = pSum - ((sum1 * sum2) / n);
		double den = Math.sqrt((sum1sq - (Math.pow(sum1, 2) / n)) * (sum2sq - (Math.pow(sum2, 2) / n))); 	
		return num/den;
	}

}
