package kmeans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Distance {
	
	public static double cosineSimilarity(Corpus corp,Documents Doc, gravityCenter c) {
		double product = 0;
		Set<String> sc = new HashSet<String>(corp.tf_idf.get(Doc.path).keySet());
		sc.retainAll(c.tf_idf.keySet());
		for(String term : sc) {
			product += (corp.tf_idf.get(Doc.path).get(term) * c.tf_idf.get(term));
		}
		return product/(Doc.norm * c.norm);
	}
	
	public static double cosineSimilarity(gravityCenter c1, gravityCenter c2) {
		double product = 0;
		Set<String> sc = new HashSet<String>(c1.tf_idf.keySet());
		sc.retainAll(c2.tf_idf.keySet());
		for(String term : sc) {
			product += (c1.tf_idf.get(term) * c2.tf_idf.get(term));
		}
		Double d1 = new Double(c1.norm);
		Double d2 = new Double(c2.norm);

		if(d1.isInfinite() ) c1.norm = 1;
		if(d2.isInfinite() ) c2.norm = 1;

		return product/(c1.norm * c2.norm);
	}
	
	public static double cosineSimilarity(ArrayList<gravityCenter> c1, ArrayList<gravityCenter> c2) {
		double product = 0;
		for(int i=0;i<c1.size();i++) {
			product+=cosineSimilarity(c1.get(i), c2.get(i));
		}

		return product/c1.size();
	}

	
	public static int distance(Corpus corp,Documents doc,ArrayList<gravityCenter> centroids) {
            int tmp = doc.cluster;
            int cluster = 1; double max = -1;double cosinesim = 0;
            for(gravityCenter c : centroids) {
                     cosinesim = cosineSimilarity(corp, doc, c);
                    if(cosinesim > max) {
                            max = cosinesim;
                            cluster = c.cluster;
                    }
            }

            return cluster;

	}

}