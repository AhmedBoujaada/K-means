package kmeans;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class TF_IDF {
	
    public static HashMap<String, Double> TF(Documents d){
        HashMap<String, Double> tf = new HashMap<String , Double>();

        Set<String> unique = new HashSet<String>(d.words);
        for (String key : unique) {
                double occ = Collections.frequency(d.words, key);
            tf.put(key,(double)occ/(double)d.words.size()); 
        }
        return tf;
    }

    public static HashMap<String, Double> IDF(HashMap<String, HashMap<String, Double>> map){
        HashMap<String, Double> idf = new HashMap<String , Double>();
        Set<String> terms = new HashSet<String>();
        for (String doc : map.keySet()) 
            terms.addAll(map.get(doc).keySet());
        int count;
        int size = 0;
        for (String term : terms) {
                count = 0;
                size = 0;
                for (String doc : map.keySet()) {
                        size ++;
                    if(map.get(doc).containsKey(term)) {
                        count ++;
                    }
                }
                if(count != size) idf.put(term,Math.log((double)size / count)) ;
        }

        return idf;

    }	

    public static void TF_IDF(Corpus c) {

        HashMap<String,HashMap<String,Double>> tf = new HashMap<String,HashMap<String,Double>>();
        for(Documents d : c.docs) {
                tf.put(d.path, TF(d));
        }

        HashMap<String, Double> idf = IDF(tf);

        HashMap<String,Double> tf_idf;
        for(Documents d : c.docs) {
            tf_idf = new HashMap<String,Double>();
            for(String term : tf.get(d.path).keySet()) {
                if(idf.containsKey(term)) {
                        tf_idf.put(term, tf.get(d.path).get(term));

                }

            }
            c.tf_idf.put(d.path, tf_idf);
        }
        for(Documents doc : c.docs) {
            double sum = 0.0;
            for(String word : c.tf_idf.get(doc.path).keySet()) {

                    sum += Math.pow(c.tf_idf.get(doc.path).get(word),2);
            }
            Double d = new Double(sum);
            if(d.isInfinite()) {
                    sum = Math.pow(Double.MIN_VALUE, 2);
            }
            doc.norm =Math.sqrt(sum);

        }
    }
}



	