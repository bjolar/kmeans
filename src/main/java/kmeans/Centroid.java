package kmeans;

import java.io.Serializable;
import java.util.ArrayList;

public class Centroid implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id = 0;
	public ArrayList<Word> wordList = new ArrayList<Word>();
	public ArrayList<Blog> blogList = new ArrayList<Blog>();
	
	public Centroid() {
	}
	
	public Centroid(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public ArrayList<Blog> getBlogList() {
    	return blogList;
    }
    
    public void clearAssigned() {
    	blogList = new ArrayList<Blog>();
    }
    
    public void assign(Blog b) {
    	blogList.add(b);
    }
    
}
