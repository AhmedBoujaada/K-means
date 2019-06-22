package kmeans;


public class Main {

    public static void main(String[] args) {
        Corpus c = new Corpus("C:\\Users\\bdsas\\Desktop\\Text Mining\\20_newsgroups\\");
        Integer[] predictClass = new Integer[c.size];
        Integer[] realClass = new Integer[c.size];
        
        for (int i = 0; i < c.size; i++) {
            predictClass[i] = 0;
                realClass[i] = 0;
        }

        TF_IDF tf_idf = new TF_IDF();

        System.out.println("Training...");
        tf_idf.TF_IDF(c);

        MobileCenter model = new MobileCenter(4);

        int j = 0;
        model.training(c);

        for(Documents d : c.docs) {
            
                predictClass[j] = d.cluster;
                realClass[j++] = d.classe;

        }

        System.out.println("Accuracy : "+Mesure.accuracy(realClass,predictClass)+"%");
        System.out.println("Precision : "+(100*Mesure.precision(realClass,predictClass))+"%");
        System.out.println("Recall : "+(100*Mesure.recall(realClass,predictClass))+"%");
        System.out.println("F1 mesure : "+Mesure.f1mesure(realClass,predictClass)+"%");

    }

}

        



