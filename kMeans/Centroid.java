import java.util.ArrayList;

public class Centroid {
    public ArrayList<Double> points = new ArrayList<Double>();
    public ArrayList<Double> newPoints = new ArrayList<Double>();
    
    public double classification;
    public double pointCount;
    
    public Centroid (ArrayList<Double> list, double classification) {
        for (int i=0;i<list.size();i++) {
            this.points.add(list.get(i));
            this.newPoints.add(i,(double) 0);
        }
        this.pointCount = 0;
        this.classification = classification;
    }
    
    public ArrayList<Double> getPoints () {
        return this.points;
    }
    
    public ArrayList<Double> getNewPoints () {
        return this.newPoints;
    }
    
    public double getClassification () {
        return this.classification;
    }
    
    public void setPoints (ArrayList<Double> list) {
        this.pointCount = this.pointCount + 1;
        for (int i=0;i<this.newPoints.size();i++) {
            double average = (list.get(i)+this.newPoints.get(i))/this.pointCount;
            this.newPoints.set(i,average);
        }
    }
    
    public void clearNewPoints () {
        this.pointCount = 0;
        for (int i=0;i<this.newPoints.size();i++) {
            this.newPoints.set(i, (double) 0);
        }
    }
    
    public void printPoints () {
        for (int i=0;i<this.points.size();i++) {
            System.out.print(" "+this.points.get(i));
        }
    }
    
    public void printNewPoints () {
        for(int i=0;i<this.newPoints.size();i++) {
            System.out.print(" "+ this.newPoints.get(i));
        }
    }
    
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
