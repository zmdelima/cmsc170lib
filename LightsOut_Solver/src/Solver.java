import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Point;
import java.util.*;

public class Solver { //Solver class
    public static int[][] cs = new int[5][5];
    int[][] tp = new int[5][5];
    
    public Solver (int[][] initConfig, int[][] initToggled) { //solver class constructor
        //creation of initial state and the frontier
        State initialState = new State(initConfig, initToggled);
        frontier f = new frontier();
        f.add(initialState);
        //creation of currentState
        State currentState = initialState;
        //mainloop for finding the solution
        while(!f.isEmpty()){
            currentState = f.removeFirst();            
            if (GoalTest(currentState)) { //condition if the currentState has the solution
                break;
            }
            else { //condition if the currentState does not have the solution yet
                for ( Point p : getAction(currentState)) {
                    f.addLast(Result(currentState,p));
                	for(int i=0;i<5;i++){
				  for(int j=0;j<5;j++){
					 tp[i][j] = currentState.legalActions[i][j];
				  }
			   	}
                }            
            }
        }
        //creation of the Solution Frame and its button/light configurations
        JFrame solframe = new JFrame("SOLUTION");
        solframe.setPreferredSize(new Dimension(200,200));
	   solframe.setResizable(false);  
        
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
              	solframe.getContentPane().setLayout(new GridLayout(5,5));
              	JButton qwe = new JButton();
              	qwe.setBackground(currentState.legalActions[i][j] == 1? Color.YELLOW : Color.BLACK);
              	qwe.setEnabled(false);
              	solframe.getContentPane().add(qwe);
            }
        }
        solframe.pack();
        solframe.setVisible(true);
        
    }

    public static class frontier extends LinkedList<State> { //class for frontier
    
    }
    
    public static class State { //This state is used to hold the current configuration of a state and its toggled coordinates
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
        
        public static State toggle (State s, int x, int y) { //constructor of State class
            s.legalActions[x][y] = 1; //set point p's coordinates into toggled ones;
            //toggle coordinate x,y of the currentState's configuration
            s.config[x][y] = s.toggleConfig(s.config[x][y]);
            if(y>0) s.config[x][y-1] = s.toggleConfig(s.config[x][y-1]);
            if(y<4) s.config[x][y+1] = s.toggleConfig(s.config[x][y+1]);
            if(x>0) s.config[x-1][y] = s.toggleConfig(s.config[x-1][y]);
            if(x<4) s.config[x+1][y] = s.toggleConfig(s.config[x+1][y]);
            //return the state
            return s;
        }
        
        public static int toggleConfig (int a) { //State class for toggling a coordinate in the 2D array of the configuration
            if ( a == 1) {
                a = 0;
            }
            else if (a == 0) { 
                a = 1;
            }
            return a;
        }
    }
    
    public static LinkedList<Point> getAction(State s) { //getAction function for sending a linkedlist of viable actions that can be done
        LinkedList<Point> p = new LinkedList<Point>();
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(s.legalActions[i][j] == 0) p.add(new Point(i,j));
            }
        }
        return p;
    }
    
    public static boolean GoalTest(State s) { //goalTest function for checking if solution has been found
        boolean res = true;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++) {
                if(s.config[i][j] == 1) res = false;        
            }
        }
        return res;
    }
    
    public static State Result(State s, Point p) { 
    	//result function for getting the result state of an Action done in toggling Point p
        State resultState = new State(s.config, s.legalActions);
        resultState.legalActions[p.x][p.y]=1;
        resultState = resultState.toggle(resultState, p.x, p.y);
        return resultState;
    }
}
