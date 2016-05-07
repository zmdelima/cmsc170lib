//packages needed
import java.util.ArrayList;

public class Centroid {
    //variables needed by the object
    public ArrayList<Double> points = new ArrayList<Double>(); 
    //'points' contains the current points of the centroid
    public ArrayList<Double> newPoints = new ArrayList<Double>(); 
    //'newPoints' contains the accumulated points of the centroid after vector classification
    public double classification;//the centroid's classification
    public double pointCount; //the centroid's vector pointCount
    
    //class constructor
    public Centroid (ArrayList<Double> list, double classification) {
        for (int i=0;i<list.size();i++) {
            this.points.add(list.get(i));
            this.newPoints.add(i,(double) 0);
        }
        this.pointCount = 0;
        this.classification = classification;
    }
    
    //returns centroid's points
    public ArrayList<Double> getPoints () {
        return this.points;
    }
    //returns centroid's newPoints
    public ArrayList<Double> getNewPoints () {
        return this.newPoints;
    }
    //returns centroid's classification
    public double getClassification () {
        return this.classification;
    }
    //calculates the centroid's newPoint values as per newly classified vector
    public void setPoints (ArrayList<Double> list) {
        this.pointCount = this.pointCount + 1;
        for (int i=0;i<(this.getNewPoints()).size();i++) {
            double curr = (this.getNewPoints()).get(i);
            double average = curr + (list.get(i) - curr)/this.pointCount;
            this.newPoints.set(i,average);
        }
    }
    //clears out centroid's newPoint values
    public void clearNewPoints () {
        this.pointCount = 0;
        for (int i=0;i<this.newPoints.size();i++) {
            this.newPoints.set(i, (double) 0);
        }
    }
    //method for printing centroid's point values
    public void printPoints () {
        for (int i=0;i<this.points.size();i++) {
            System.out.print(" "+this.points.get(i));
        }
    }
    //method for printing centroid's newPoint values
    public void printNewPoints () {
        for(int i=0;i<this.newPoints.size();i++) {
            System.out.print(" "+ this.newPoints.get(i));
        }
    }
    
    //method for determining change or difference between
    //the centroid's newPoints and the points values 
    public boolean didChange() {
        boolean change = false;
        for (int i=0;i<this.points.size();i++) {
            if ((double) (this.getPoints()).get(i) - (double) (this.getNewPoints()).get(i) != 0) {
               change = true;
               break;
            }
        }
        if (change) {
            for (int j=0;j<this.points.size();j++) {
                (this.getPoints()).set(j,(this.getNewPoints()).get(j));
            }
        }
        return change;
    }
}
