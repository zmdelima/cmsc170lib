//packages needed
import java.util.ArrayList;

public class Vect {
    //variables used by the object
    public ArrayList<Double> points = new ArrayList<Double>();
    //'points' refer to the points of the vector object
    public double classification;
    //the vector's classification
    public double minDist;
    //the vector's minimum distance as per detected with its new classification
    
    //constructor and initialization of values
    public Vect (ArrayList<Double> list) {
        for (int i=0;i<list.size();i++) {
            this.points.add(list.get(i));
        }
        this.classification = 0;
        this.minDist = -1;
    }
    //returns vector's points
    public ArrayList<Double> getPoints () {
        return this.points;
    }
    //returns vector's classification
    public double getClassification () {
        return this.classification;
    }
    //resets vector's classification and minDist
    public void resetClass () {
        this.classification = 0;
        this.minDist = -1;
    }
    //method for calculating vector distance
    //with centroid's points and classification as parameters
    public void identifyClass (ArrayList<Double> c, double classification) {
        double dist = 0;
        //calculating distance
        for (int i=0;i<this.points.size();i++) {
            dist = dist + Math.pow ((this.points.get(i) - c.get(i)), 2);   
        }
        //squarerooting the accumulated distance
        dist = Math.sqrt(dist);
        //comparing if it is closer/initial classification value
        if (this.minDist > dist || this.minDist == -1) {
            this.classification = classification;
            this.minDist = dist;
        }
    }
    //method for printing vector points
    public void printPoints () {
        for (int i=0;i<this.points.size();i++) {
            System.out.print(" "+this.points.get(i));
        }
    }
}
