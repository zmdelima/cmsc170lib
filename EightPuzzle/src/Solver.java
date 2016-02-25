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
    public static int i;
    public static int j;
    
    //Solver class constructor
    public Solver (int[][] initConfig, int[][] initToggled) { 
        //creation of initial state and the frontier
        State initialState = new State(initConfig, initToggled,0,0,0,null);
        //initial state lists
        if(!checkSolvable(initialState)){
            JOptionPane.showMessageDialog(new JFrame(),"Tile Configuration Unsolvable!","Unsolvable Puzzle!",JOptionPane.WARNING_MESSAGE);
            return;
        }
        BufferedWriter output = null;
        
        stateList openList = new stateList();
        stateList closedList = new stateList();
        
        stateList solutionList = new stateList();
        
        //initialization of openList
        openList.add(initialState);
        //creation of currentState
        State bestState = initialState;
        
        //mainloop for finding the solution
        while(!openList.isEmpty()){
            bestState = openList.removeFirst();            
            closedList.add(bestState);
            if (GoalTest(bestState)) { //condition if the currentState has the solution
                break;
            }
            else { //condition if the currentState does not have the solution yet
                for ( Point p : getAction(bestState)) {
                    State s = (Result(bestState,p));
                	int oRes=-1,cRes=-1;
                	
                	//scanning for same state configuration
                	for (i=0;i<openList.size();i++){
                	    if (stateComp(s,openList.get(i))) {
                	        if(s.cost < openList.get(i).cost){
                    	        oRes = i;
                    	        break;
                	        }
                	    } 
                	}
                	
                	//scanning for same state configuration
                	for (j=0;j<closedList.size();j++){
                    	if (stateComp(s,closedList.get(j))) {
                	        if (s.cost < closedList.get(j).cost) {
                    	        cRes = j;
                    	        break;
                	        }
                	    }
                	}
                	
                	//if -1 on both closed and open add to openList, where? search
                	if (oRes < 0 && cRes < 0){
                	    for(i=0;i<openList.size();i++){
                	        if (s.cost < openList.get(i).cost) {
                	            openList.add(i,s);
                	            break;
                	        }
                	    }
                	    if (i == openList.size()) {
                	        openList.add(i,s);
                	    }
                	}
                	
                	//if -1 on closed but 0 <= open -> open.remove(o), open.add(o,s)
                	else if (oRes >= 0 && cRes < 0) {
                	    openList.remove(oRes);
                	    openList.add(oRes,s);
                	}
                	
                	//if 0<= closed but -1 on open  -> closed.remove(c), closed.add(c,s)
                	else if (oRes < 0 && cRes >= 0) {
                	    closedList.remove(cRes);
                	    closedList.add(cRes,s);
                	} 
                	//if 0<= closed and 0<= open    -> open.remove(o), closed.remove(c), closed.add(c,s)
                	else if (oRes >= 0 && cRes >=0) {
                	    openList.remove(oRes);
                	    closedList.remove(cRes);
                	    closedList.add(cRes,s);
                	}
                }            
            }
        }
        
        //grouping up of each state solution
        while(bestState.parent != null){
            solutionList.addFirst(bestState);
            bestState = bestState.parent;
        }
        solutionList.addFirst(initialState);
                
        
        //creation of the Solution File
        try {
            String sol = "";
            File file = new File("8puzzle.out");
            output = new BufferedWriter(new FileWriter(file));
            
            while(!solutionList.isEmpty()){
                State s = solutionList.removeFirst();
                for(i=0;i<3;i++){
                    for(j=0;j<3;j++){
                        sol = sol+" "+s.config[i][j];
                    }
                    sol = sol +"\n";
                }
                sol = sol+"----------\n";
            }
            
        } catch (IOException ex) {
          ex.printStackTrace();
        } finally {
           try {
            output.close();
           } catch (Exception ee) {
            ee.printStackTrace();
           }
        }
        
        
    }
    
    //class stateList for openList and closedList, a linkedlist(container) of 'States'
    public static class stateList extends LinkedList<State> { //class for openlist and closedlist
    
    }
    //getAction function for sending a linkedlist of viable actions that can be done
    public static LinkedList<Point> getAction(State s) { 
        LinkedList<Point> p = new LinkedList<Point>();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(s.legalActions[i][j] == 1) p.add(new Point(i,j));
            }
        }
        return p;
    }
    
    //goalTest function for checking if solution has been found
    public static boolean GoalTest(State s) { 
        boolean res = true;
        for(i=0;i<3;i++){
            for(j=0;j<3;j++) {
            	if(i==2 && j==2) break;
                if(s.config[i][j] != (i*3 +(j+1))) res = false;        
            }
        }
        return res;
    }
    
    public static boolean stateComp(State a, State b) {
        for(i=0;i<3;i++){
            for(j=0;j<3;j++){
                if(a.config[i][j] != b.config[i][j]) return false;
            }
        }
        return true;
    }
    
    
    //result function for getting the result state of an Action done in toggling Point p
    public static State Result(State s, Point p) { 
        int[] zero = new int[2];
        State resultState = new State(s.config, s.legalActions, s.cost, s.dist, s.total, s);
        
        //getting zeroth tile position or coordinate
        zero = s.getZero();
        
        //toggling the action given from point p
        resultState = resultState.toggle(resultState, p.x, p.y,zero[0],zero[1]);
        resultState.getLegalActions(p.x,p.y);
        return resultState;
    }
    
    
    //method for checking 
    public static boolean checkSolvable(State s){
        int[] list = new int[8];
        int inv=0;
        for(i=0;i<3;i++){
            for(j=0;j<3;j++){
                if(s.config[i][j]==0)continue;
                list[(i*3)+j]=s.config[i][j];
            }
        }
        
        for(i=0;i<list.length;i++){
            for(j=i+1;j<list.length;j++){
                if(list[j]>list[i]){
                    inv++;
                }
            }
        }
    
        if(inv%2 == 1)return false;
        return true;
    }
    //STATE OBJECT CLASS
    public static class State { //This state is used to hold the current configuration of a state and its toggled coordinates
        public static int[][] config = new int[3][3];
        public static int[][] legalActions = new int[3][3];
        public static int cost;
        public static int dist;
        public static int total;  
        public static State parent;
        
        //State constructor
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
        
        //method for toggling state tile configuration
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
        
        //method for new state for increasing its cost by 1
        public void upCost() {
        	this.cost = this.cost + 1;
        }
        
        //method for new state of computing its manhattan distance
        public void setDist() {
        	int curr, total=0, origX=0, origY=0;
        	
        	for(i=0;i<3;i++){
        		for(j=0;j<3;j++){
            		curr = this.config[i][j];
            		if (curr==0) continue;
            		origX = (curr-1)/3; //row no. formula number-(adjustments raise while number increases from 0 from initial 0,0)/ dimensions
            		origY = (curr+3)%3; //col no. formula (number+dimensions) % dimensions
            		total = total + Math.abs(i-origX) + Math.abs(j-origY);
        		}
        	}
        	this.total=total;
        }
        
        //method for new state of adding its cost and manhattan distance
        public void setTotal() {
        	this.total = this.cost + this.dist;
        }
        
        //method for setting a State's legalActions from toggling tile(a,b)
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
        
        //method for getting the coordinates of the tile with zero value
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
    
    
}
