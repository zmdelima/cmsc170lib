import java.awt.Point;
import java.util.*;

public class Solver {
    public static int[][] cs = new int[5][5];
    int[][] tp = new int[5][5];
    
    public Solver (int[][] initConfig, int[][] initToggled) {
        
        State initialState = new State(initConfig, initToggled);
         
        frontier f = new frontier();
        f.add(initialState);
        State currentState = initialState;
        while(!f.isEmpty()){
            currentState = f.removeFirst();            
            if (GoalTest(currentState)) {
                System.out.println("FOUND IT!");
                break;
            }
            else {
                for ( Point p : getAction(currentState)) {
                    f.add(Result(currentState,p));
                }            
            }
        }
        
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                tp[i][j] = currentState.legalActions[i][j];
                System.out.print(currentState.config[i][j]);
                
            }
            System.out.print("\n");
        }
        
    }

    public static class frontier extends LinkedList<State> {
    
    }
    
    public static class State {
        int[][] config = new int[5][5];
        int[][] legalActions = new int[5][5];  
        public State (int[][] a, int[][] b) {
            for(int i=0;i<5;i++){
                for(int j=0;j<5;j++){
                    config[i][j] = a[i][j];
                    legalActions[i][j] = b[i][j];
                }
            }
        }
        
        public static State toggle (State s, int x, int y) {
            s.legalActions[x][y] = 1; //set point p's coordinates into toggled ones;
            s.config[x][y] = s.toggleConfig(s.config[x][y]);
            if(y>0) s.config[x][y-1] = s.toggleConfig(s.config[x][y-1]);
            if(y<4) s.config[x][y+1] = s.toggleConfig(s.config[x][y+1]);
            if(x>0) s.config[x-1][y] = s.toggleConfig(s.config[x-1][y]);
            if(x<4) s.config[x+1][y] = s.toggleConfig(s.config[x+1][y]);
            
            return s;
        }
        
        public static int toggleConfig (int a) {
            if ( a == 1) {
                a = 0;
            }
            else if (a == 0) { 
                a = 1;
            }
            return a;
        }
    }
    
    public static LinkedList<Point> getAction(State s) {
        LinkedList<Point> p = new LinkedList<Point>();
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(s.legalActions[i][j] == 0) p.add(new Point(i,j));
            }
        }
        return p;
    }
    
    public static boolean GoalTest(State s) {
        boolean res = true;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++) {
                if(s.config[i][j] == 1) res = false;        
            }
        }
        return res;
    }
    
    public static State Result(State s, Point p) {
        State resultState = new State(s.config, s.legalActions);
        resultState.legalActions[p.x][p.y]=1;
        resultState = resultState.toggle(s, p.x, p.y);
        return resultState;
    }
}
