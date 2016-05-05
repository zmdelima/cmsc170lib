import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class kMeans{
    public static void main (String args[]) {
        ArrayList<Object> vectorMap = new ArrayList<Object>();
        ArrayList<Object> cenList = new ArrayList<Object>();
        int k           = 0;
        int counter     = 0;
        int old_dim     = -1;
        int dim         = 0;
        int iteration   = 0;
        int stop        = 0;
        int i           = 0;
        int j           = 0;
        //reading area
        File file = new File("training.txt");
        
        try{
                BufferedReader br = new BufferedReader(new FileReader(file));
                String s;
                
                k = Integer.parseInt(br.readLine());
                while ((s = br.readLine()) != null) {
                    //read per line here
                    String[] row = s.split(" ");
                    
                    dim = row.length;
                    ArrayList<Double> p = new ArrayList<Double>();
                    
                    for (i=0;i<row.length;i++) {
                        p.add(i,Double.parseDouble(row[i]));
                    }
                    
                    if(old_dim != -1 && old_dim!= dim){
                            System.out.println("Inconsistency in number of dimensions in training.txt!");
                    }
                    
                    //create Vect
                    Vect v = new Vect(p);  
                    //add to hashMap
                    vectorMap.add(counter,v);
                    old_dim = dim;
                    counter = counter + 1;
                }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        
        //randomize values   
        ArrayList<Integer> randTemp = new ArrayList<Integer>();
        for(i=0;i<k;i++){
            int rand = (new Random()).nextInt(counter);
            if (!(randTemp.contains(rand))) {
                randTemp.add(rand);
                Centroid c = new Centroid(((Vect) vectorMap.get(rand)).getPoints() ,(double) i);
                cenList.add(c);
                i = i + 1;
            }
            i = i - 1;
        }
        
        //Initial random centroid vector printing
        System.out.println("Iteration "+iteration+":");
        for (i=0;i<cenList.size();i++) {
            System.out.print("Centroid "+(i+1)+":");
            ((Centroid) cenList.get(i)).printPoints();
            System.out.println();
        }
        
        //kMean loop
        while (stop == 0) {
            stop = 1;
            iteration = iteration + 1;
            for (i=0;i<vectorMap.size();i++) {
                
                for (j=0;j<cenList.size();j++) {
                    ((Vect)vectorMap.get(i)).identifyClass( ((Centroid) cenList.get(j)).getPoints(), ((Centroid)cenList.get(j)).getClassification());
                }
                int temp = (int) ((Vect) vectorMap.get(i)).getClassification();
                ((Centroid) cenList.get(temp)).setPoints(((Vect) vectorMap.get(i)).getPoints());
            }
            
            for (i=0;i<cenList.size();i++) {
                if (((Centroid) cenList.get(i)).didChange()) {
                    stop = 0;
                }
            }
            
            
            System.out.println("Iteration "+iteration+":");
            for (i=0;i<cenList.size();i++) {
                System.out.print("Centroid "+(i+1)+":");
                ((Centroid) cenList.get(i)).printPoints();
                System.out.println();                
                ((Centroid) cenList.get(i)).clearNewPoints();
            }
        }
        
        System.out.println("Final Centroid Values");
        
        for (i=0;i<cenList.size();i++) {
                System.out.print("Centroid "+(i+1)+":");
                ((Centroid) cenList.get(i)).printPoints();
                System.out.println();
        }
    }
}
