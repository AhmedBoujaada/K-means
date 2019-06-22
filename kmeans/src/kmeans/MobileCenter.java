package kmeans;

import java.util.*;


public class MobileCenter {
	
    public int k;
    
    public ArrayList<gravityCenter> gravity_center;
    public ArrayList<gravityCenter> gravity_center_old;


    public MobileCenter(int k) {
        this.k=k;
        this.gravity_center = new ArrayList<gravityCenter>(k);
        this.gravity_center_old = new ArrayList<gravityCenter>(k);

    }
    public void training(Corpus corp) {

        ArrayList<Integer> list = new ArrayList<Integer>(corp.size);
        for(int i = 1; i <= corp.size; i++) {
            list.add(i);
        }
        Random rand = new Random();
        int num =this.k;
        int cluster =1;
        while(num>0) {
            int index = rand.nextInt(list.size());
           if(cluster == corp.docs.get(index).classe) {
                list.remove(index);
                this.gravity_center.add(new gravityCenter(cluster, corp.docs.get(index), corp));
                cluster ++;num--;

            }
        }

        cluster = 0;

        do {
            cluster++;
            for(Documents doc : corp.docs) {
                    doc.cluster = Distance.distance(corp, doc, this.gravity_center);
            }
            this.gravity_center_old = new ArrayList<gravityCenter>();
            for(int i=0;i<this.gravity_center.size();i++) {
                    this.gravity_center_old.add(new gravityCenter(new Documents(this.gravity_center.get(i)), this.gravity_center.get(i)));
            }
            int j = 1;
            for(gravityCenter c : this.gravity_center) {
                    c.update(corp);
            }
        } while (Distance.cosineSimilarity(this.gravity_center_old, this.gravity_center) < 0.99 );


    }
	
}


class gravityCenter extends Documents {
    
    HashMap<String,Double> tf_idf;

    public gravityCenter(int cluster,Documents d,Corpus c) {
            super(cluster);
            this.tf_idf = new HashMap<String,Double>(c.tf_idf.get(d.path));
            this.norm = d.norm;

    }
    public gravityCenter(Documents dd,gravityCenter d) {
            super(dd);
            this.tf_idf = new HashMap<String,Double>(d.tf_idf);
            this.norm = d.norm;
    }

    public void update(Corpus c) {

            HashMap<String, Double> tf_idf_new = new HashMap<String,Double>();
            tf_idf_new.putAll(this.tf_idf);
            int n=0;
            for(Documents doc : c.docs) {
                    if(this.cluster == doc.cluster) {
                            n++;
                            for(String word : c.tf_idf.get(doc.path).keySet()) {
                                    if(!tf_idf_new.containsKey(word)) {
                                            tf_idf_new.put(word, 0.0);
                                    }
                                    tf_idf_new.replace(word, tf_idf_new.get(word) + c.tf_idf.get(doc.path).get(word));
                            }
                    }
            }
            double sum = 0.0;
            for(String word : tf_idf_new.keySet()) {

                    tf_idf_new.replace(word, tf_idf_new.get(word)/n);
                    sum += Math.pow(tf_idf_new.get(word),2);
            }
            this.tf_idf = new HashMap<String,Double>(tf_idf_new);
            Double d = new Double(sum);
            if(d.isInfinite()) {
                    sum = Math.pow(Double.MIN_VALUE, 2);
            }
            else {
                    d = new Double(Math.sqrt(sum));
                    if(d.isInfinite()) {

                            sum = 0.01;
                    }
            }

            this.norm = Math.sqrt(sum);

    }

}

	