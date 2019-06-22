package kmeans;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class Corpus {
	
	public HashMap<String, HashMap<String,Double>> tf_idf;
	public ArrayList<Documents> docs;
	public int size;
	
	public Corpus(String path){
		this.docs = new ArrayList<Documents>();
		this.tf_idf = new HashMap<String, HashMap<String,Double>>();
		this.size = 0;
		File directory = new File(path);
		File[] folders = directory.listFiles();
		for (File doc : folders) {
			Documents d = new Documents(doc.getPath());
			d.load();
			this.add(d);
		}
		
	}
	

	public void add(Documents d){
		this.docs.add(d);
		this.tf_idf.put(d.path, new HashMap<String,Double>());
		this.size ++;
	}

}