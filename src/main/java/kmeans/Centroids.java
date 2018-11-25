package kmeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Centroids implements Serializable {

	private static final long serialVersionUID = 1L;
	int id = 0;
	int k = 5;
	int iteration =  5;
	public ArrayList<Word> allwords = new ArrayList<Word>();
	public ArrayList<Blog> allblogs = new ArrayList<Blog>();
	public ArrayList<Centroid> allcentroids = new ArrayList<Centroid>();

	public int getId() {
		return id;
	}

	public Centroids() {
	}
	
	public Centroids(int k) {
        this.k = k;
    }
	
	public void initsializeCentroids() {
		int size = allwords.size();
		for (int i = 0; i < k; i++) {
			Centroid c = new Centroid();			
			for (int j = 0; j < size; j++) {
				Random r = new Random();
				int occ = r.nextInt((allwords.get(j).max - allwords.get(j).min) + 1) + allwords.get(j).min;
				Word w = new Word();
				w.word = allwords.get(j).word;
				w.count = occ;
				c.wordList.add(w);
			}
			allcentroids.add(c);
		}
	}
	
	public void assignCentroids() {
		int size = allblogs.size();	
		for (int i = 0; i < iteration; i++) {
			clearAssignments();
			for (Blog b : allblogs) {
				double distance = Double.MAX_VALUE;
				Centroid best = new Centroid();
				for (Centroid c : allcentroids) {
					double cDist = pearson(c, b);
					if (cDist < distance) {
						best = c;
						distance = cDist;
					}
				}
				best.assign(b);
			}
			
			//Re-calculate center for each centroid
			for (Centroid c : allcentroids) {
				int wordSize = allwords.size();
				for (int j = 0; j < wordSize; j++) {
					double avg = 0.0;
					for (Blog b : c.blogList) {
						avg += b.wordList.get(j).occurance;
					}
					avg /= c.blogList.size();
					c.wordList.get(j).count = avg;
				}
			}
		}
	}
	
	/**
	 * Calculate pearson correlation score
	 * @param centroid
	 * @param second
	 * @return
	 */
	public double pearson(Centroid centroid, Blog second) {
		double sum1 = 0.0;
		double sum2 = 0.0;
		double sum1sq = 0.0;
		double sum2sq = 0.0;
		double pSum = 0.0;
		int n = 0;
		int size = centroid.wordList.size();
		for (int i = 0; i < size; i++) {
			Word firstWordcentWord = centroid.wordList.get(i);
			Word secondWord = second.wordList.get(i);
//			System.out.println(firstWord.word);
//			System.out.println(firstWord.word);
//			System.exit(0);
				
			sum1 += firstWordcentWord.count;
			sum2 += secondWord.occurance;
			sum1sq += Math.pow(firstWordcentWord.count, 2);
			sum2sq += Math.pow(secondWord.occurance, 2);
			pSum += firstWordcentWord.count * secondWord.occurance;
			n += 1;
				
		}
		
		if (n == 0) {
			return 0.0;
		}
		double num = pSum - ((sum1 * sum2) / n);
		double den = Math.sqrt((sum1sq - (Math.pow(sum1, 2) / n)) * (sum2sq - (Math.pow(sum2, 2) / n))); 	
		return num/den;
	}
	
	public void clearAssignments() {
		for (Centroid c : allcentroids) {
			c.clearAssigned();
		}
	}
}
