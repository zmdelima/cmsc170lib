import java.util.ArrayList;

public class Vect {
    public ArrayList<Double> points = new ArrayList<Double>();
    public double classification;
    public double minDist;
    
    public Vect (ArrayList<Double> list) {
        for (int i=0;i<list.size();i++) {
            this.points.add(list.get(i));
        }
        this.classification = 0;
        this.minDist = -1;
    }
    
    public ArrayList<Double> getPoints () {
        return this.points;
    }
    
    public double getClassification () {
        return this.classification;
    }
    
    public void resetClass () {
        this.classification=0;
    }
    
    public void identifyClass (ArrayList<Double> c, double classification) {
        double dist = 0;
        
        for (int i=0;i<this.points.size();i++) {
            dist = dist + Math.pow ((this.points.get(i) - c.get(i)), 2);   
        }
        
        dist = Math.sqrt(dist);
        System.out.print("Centroid"+(int) (classification+1)+" distance: "+dist);
        
        if (this.minDist > dist || this.minDist == -1) {
            this.classification = classification;
            this.minDist = dist;
        }
    }
    
    public void printPoints () {
        for (int i=0;i<this.points.size();i++) {
            System.out.print(" "+this.points.get(i));
        }
    }
}