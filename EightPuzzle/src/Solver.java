//the packages
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Point;
import java.util.*;
import java.lang.Math;

public class Solver { //Solver class
    //the global variables
    public static HashMap <Integer, Point> gg = new HashMap<Integer, Point>();
    public static int i;
    public static int j;
    
    public Solver (int[][] initConfig, int[][] initToggled) { //solver class constructor
        //creation of initial state and the frontier
        State initialState = new State(initConfig, initToggled,0,0,0,null);
        //initial state lists
        stateList openList = new stateList();
        stateList closedList = new stateList();
        
        //initialization of openList
        openList.add(initialState);
        //creation of currentState
        State bestState = initialState;
        for(i=0;i<3;i++){
        	for(j=0;j<3;j++){
        		if(i==2 && j==2)gg.put(0,new Point(2,2));
        		gg.put((i*3 +(j+1)),new Point(i,j));
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
                    State s = new State(bestState.config, bestState.legalActions, bestState.cost, bestState.dist, bestState.total, bestState);
                    openList.addLast(Result(bestState,p));
                	
                }            
            }
        }
        //creation of the Solution Frame and its button/light configurations
        JFrame solframe = new JFrame("SOLUTION");
        solframe.setPreferredSize(new Dimension(200,200));
	    solframe.setResizable(false);  
        solframe.pack();
        solframe.setVisible(true);
        
    }

    public static class stateList extends LinkedList<State> { //class for openlist and closedlist
    
    }
    
    public static class State { //This state is used to hold the current configuration of a state and its toggled coordinates
        public static int[][] config = new int[3][3];
        public static int[][] legalActions = new int[3][3];
        public static int cost;
        public static int dist;
        public static int total;  
        public static State parent;
        
        public State (int[][] a, int[][] b, int cost, int dist, int total, State s) {
            for(i=0;i<3;i++){
                for(j=0;j<3;j++){
        			this.config[i][j] = a[i][j];
        			this.legalActions[i][j] = b[i][j];        
                }
            }
            this.parent = s;
            this.cost = cost;
            this.dist = dist;
            this.total = total;
        }
        
        public static State toggle (State s, int x, int y, int vert, int hori) { //constructor of State class
            int temp;
            //toggle coordinate x,y of the currentState's configuration
            temp = s.config[x][y];
            s.config[x][y] = s.config[vert][hori];
            s.config[vert][hori] = temp;
		    s.upCost();
		    s.setDist();
		    s.setTotal();
            //return the state
            return s;
        }
        
        public void upCost() {
        	this.cost = this.cost + 1;
        }
        
        public void setDist() {
        	int curr, total=0;
        	
        	for(i=0;i<3;i++){
        		for(j=0;j<3;j++){
            		curr = this.config[i][j];
            		if (curr==0)continue;
            		total = total + Math.abs(i-(gg.get(curr)).x) + Math.abs(j-(gg.get(curr)).y);
        		}
        	}
        	this.total=total;
        }
        
        public void setTotal() {
        	this.total = this.cost + this.dist;
        }
        
        public void getLegalActions(int a, int b) {
        	for(int i=0;i<3;i++){
        		for(int j=0;j<3;j++){
        			this.legalActions[0][0]=0;
        		}
        	}
          if(b>0) this.legalActions[a][b-1] = 1;
          if(b<4) this.legalActions[a][b+1] = 1;
          if(a>0) this.legalActions[a-1][b] = 1;
          if(a<4) this.legalActions[a+1][b] = 1;
        	
        }
        
        public int[] getZero() {
        	int[] zero = new int[2];
        	for(i=0;i<3;i++){
        		for(j=0;j<3;j++){
        			if(this.config[i][j] == 0) {
        				zero[0]=i;
        				zero[1]=j;
        				return zero;
        			}
        		}
        	}
        	return zero;
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
        for(i=0;i<3;i++){
            for(j=0;j<3;j++) {
            	 if(i==2 && j==2) break;
                if(s.config[i][j] != (i*3 +(j+1))) res = false;        
            }
        }
        return res;
    }
    
    public static State Result(State s, Point p) { 
    	//result function for getting the result state of an Action done in toggling Point p
        int[] zero = new int[2];
        State resultState = new State(s.config, s.legalActions, s.cost, s.dist, s.total, s);
        zero = s.getZero();
        resultState = resultState.toggle(resultState, p.x, p.y,zero[0],zero[1]);
        resultState.getLegalActions(p.x,p.y);
        resultState.setParent(s);
        return resultState;
    }
}
