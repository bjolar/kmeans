package kmeans;

import java.io.Serializable;
import java.util.ArrayList;

public class Blog implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public ArrayList<Word> wordList = new ArrayList<Word>();
	
	public Blog() {
	}
	
	public Blog(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
