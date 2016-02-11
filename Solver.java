import java.awt.Point;
import java.util.*;

public class Solver {
    public static int[][] cs = new int[5][5];
    int[][] tp = new int[5][5];
    
    public Solver (int[][] initConfig, int[][] initToggled) {
        
        State initialState = new State(initConfig, initToggled);
         
        frontier f = new frontier();
        f.add(initialState);
        
        while(!f.isEmpty()){
            State currentState = f.removeFirst();            
            if (GoalTest(curr)) {            
                break;
            }
            else {
                for ( Point p : getActions(currentState)) {
                    frontier.add(Result(currentState s, p)
                }            
            }
        }
        
    }

    public static class frontier extends LinkedList<State> {
    
    }
    
    public class State {
        int[][] config = new int[5][5];
        int[][] legalActions = new int[5][5];  
        public State (int[][] a, int[][] b){
            for(int i=0;i<5;i++){
                for(int j=0;j<5;j++){
                    config[i][j] = a[i][j];
                    legalActions[i][j] = b[i][j];
                }
            }
        }
        
        public static int[][] toggle (int x, int y) {
            this.legalActions[x][y] == 1; //set point p's coordinates into toggled ones;
            this.config[x][y] = this.toggleConfig(config[x][y]);
            if(y>0) this.config[x][y-1] = this.toggleConfig(this.config[x][y-1]);
            if(y<4) this.config[x][y+1] = this.toggleConfig(this.config[x][y+1]);
            if(x>0) this.config[x-1][y] = this.toggleConfig(this.config[x-1][y]);
            if(x<4) this.config[x+1][y] = this.toggleConfig(this.config[x+1][y]);
        }
        
        public static int toggleConfig (int a) {
            if ( a == 1) return 0;
            else return 1;
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
        resultState.toggle(p.x, p.y);
        return resultState;
    }
    /*
    test = frontier.removeFirst();
        //create toggle(state b, action a) --> for Result
        
        //create for(action a : getAction(currentState)){
            frontier.add(Result(currentState s, action(Point) a)
        }
        //createGoalTest(that returns either true or false)
        
    */
}
