import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Point;
import java.util.*;

public class Solver { //Solver class
    public static HashMap <int, Point> gg = new HashMap<int, Point>();
     
    public Solver (int[][] initConfig, int[][] initToggled) { //solver class constructor
        //creation of initial state and the frontier
        State initialState = new State(initConfig, initToggled);
        //initial state list
        openList.add(initialState);
        //creation of currentState
        State bestState = initialState;
        int i,j;
        for(int i=0;i<3;i++){
        	for(int j=0;j<3;j++){
        		if(i==2 && j==2)gg.add(0,new Point(2,2));
        		gg.add((i*3 +(j+1)),new Point(i,j));
        	}
        }
        
        //mainloop for finding the solution
        while(!openList.isEmpty()){
            bestState = openList.removeFirst();            
            closedList.add(bestState);
            if (GoalTest(bestState)) { //condition if the currentState has the solution
                break;
            }
            else { //condition if the currentState does not have the solution yet
                for ( Point p : getAction(bestState)) {
                    s = bestState;
                    openList.addLast(Result(bestState,p));
                	
                }            
            }
        }
        //creation of the Solution Frame and its button/light configurations
        JFrame solframe = new JFrame("SOLUTION");
        solframe.setPreferredSize(new Dimension(200,200));
	   solframe.setResizable(false);  
        /*
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
              	solframe.getContentPane().setLayout(new GridLayout(3,3));
              	JButton qwe = new JButton();
              	qwe.setBackground(bestState.legalActions[i][j] == 1? Color.YELLOW : Color.BLACK);
              	qwe.setEnabled(false);
              	solframe.getContentPane().add(qwe);
            }
        }*/
        solframe.pack();
        solframe.setVisible(true);
        
    }

    public static class openList extends LinkedList<State> { //class for frontier
    
    }
    
    public static class closedList extends LinkedList<State> { //class for frontier
    
    }
    
    public static class State { //This state is used to hold the current configuration of a state and its toggled coordinates
        int[][] config = new int[3][3];
        int[][] legalActions = new int[3][3];
        int cost,dist,total;  
        State parent;
        public State (int[][] a, int[][] b) {
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
        			config = a[i][j];
        			legalActions = b[i][j];        
                }	
            }
        }
        
        public static State toggle (State a, int x, int y, int vert, int hori) { //constructor of State class
            int temp;
            //toggle coordinate x,y of the currentState's configuration
            temp = s.config[x][y];
            s.config[x][y] = s.config[vert][hori];
            s.config[vert][hori] = temp;
		  s.setCost(s);
		  s.setDist(s);
		  s.setTotal(s);
            //return the state
            return s;
        }
        
        public static void setCost(s) {
        	this.cost = this.cost + 1;
        }
        
        public static void setDist(s) {
        	int total=0;
        	for(int i=0;i<3;i++){
        		for(int j=0;j<3;j++){
        		int curr = this.config[i][j];
        		if (curr==0)continue;
        		total = total + abs(i-gg(curr).getX()) + abs(j-gg(curr).getY());
        		}
        	}
        	this.total=total;
        }
        
        public static void setTotal(s) {
        	this.total = this.cost + this.dist;
        }
        
        public static void setParent(State s) {
        	this.parent = s;
        }
        
        public static void getLegalActions(int a, int b) {
        	for(int i=0;i<3;i++){
        		for(int j=0;j<3;j++){
        			this.config[0][0]=0;
        		}
        	}
        	if(b>0) this.config[a][b-1] = 1;
          if(b<4) this.config[a][b+1] = 1;
          if(a>0) this.config[a-1][b] = 1;
          if(a<4) this.config[a+1][b] = 1;
        	
        }
        
        public int[] static void getZero() {
        	int[] zero = new int[2];
        	for(int i=0;i<3;i++){
        		for(int j=0;j<3;j++){
        			if(this.config[i][j] == 0) {
        				zero[0]=i;
        				zero[1]=j;
        				return zero;
        			}
        		}
        	}
        }
    }
    
    public static LinkedList<Point> getAction(State s) { //getAction function for sending a linkedlist of viable actions that can be done
        LinkedList<Point> p = new LinkedList<Point>();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(s.legalActions[i][j] == 1) p.add(new Point(i,j));
            }
        }
        return p;
    }
    
    public static boolean GoalTest(State s) { //goalTest function for checking if solution has been found
        boolean res = true;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++) {
            	 if(i==2 && j==2) break;
                if(s.config[i][j] != (i*3 +(j+1))) res = false;        
            }
        }
        return res;
    }
    
    public static State Result(State s, Point p) { 
    	//result function for getting the result state of an Action done in toggling Point p
        int[] zero = new int[2];
        State resultState = new State(s.config, s.legalActions);
        zero = s.getZero();
        resultState = resultState.toggle(resultState, p.x, p.y,zero[0],zero[1]);
        resultState.getLegalActions(zero[0],zero[1]);
        resultState.setParent(s);
        return resultState;
    }
}
