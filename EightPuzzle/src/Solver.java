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
    public stateList solutionList = new stateList();
    
    //Solver class constructor
    public Solver (int[][] initConfig, int[][] initToggled) { 
        //creation of initial state and the frontier
        State initialState = new State(initConfig, initToggled,-1,0,0,null);
        //initial state lists
        initialState.setDist();
        initialState.setTotal();
        
        
        if(!checkSolvable(initialState)){
            JOptionPane.showMessageDialog(new JFrame(),"Tile Configuration Unsolvable!","Unsolvable Puzzle!",JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        BufferedWriter output = null;
        
        stateList openList = new stateList();
        stateList closedList = new stateList();
        
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
                    State s = Result(bestState,p);
                	int i,j,oRes=-1,cRes=-1;
                	
                	//scanning for same state configuration
                	for (i=0;i<openList.size();i++){
                	    if (stateComp(s,openList.get(i))) {
                	        if(s.getCost() < openList.get(i).getCost()){
                    	        oRes = i;
                    	        break;
                	        }
                	    } 
                	}
                	
                	//scanning for same state configuration
                	for (j=0;j<closedList.size();j++){
                    	if (stateComp(s,closedList.get(j))) {
                	        if (s.getCost() < closedList.get(j).getCost()) {
                    	        cRes = j;
                    	        break;
                	        }
                	    }
                	}
                	
                	//if -1 on both closed and open add to openList, where? search
                	if (oRes < 0 && cRes < 0){
                	    for(i=0;i<openList.size();i++){
                	        if (s.getTotal() < openList.get(i).getTotal()) {
                	            openList.add(i,s);
                	            break;
                	        }
                	    }
                	    if (i == openList.size() && !openList.contains(s)) {
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
                	    openList.add(openList.size(),s);
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
            sol = sol+"START\n----------\n";
            for(State s : solutionList){
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        sol = sol+" "+s.getConfig(i,j);
                    }
                    sol = sol +"\n";
                }
                sol = sol+"----------\n";
            }
            output.write(sol,0,sol.length());
            JOptionPane.showMessageDialog(new JFrame(),"Successfully created solution in \"8puzzle.out\" file","Puzzle Solved!",JOptionPane.INFORMATION_MESSAGE);  
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
    
    public stateList getList(){
        return this.solutionList;
    }
    
    //getAction function for sending a linkedlist of viable actions that can be done
    public static LinkedList<Point> getAction(State s) { 
        LinkedList<Point> p = new LinkedList<Point>();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(s.getLegalAction(i,j) == 1) p.add(new Point(i,j));
            }
        }
        return p;
    }
    
    //goalTest function for checking if solution has been found
    public static boolean GoalTest(State s) {
         
        if(s.getDist()==0)return true;
        return false;
    }
    
    public static boolean stateComp(State a, State b) {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(a.getConfig(i,j) != b.getConfig(i,j)) return false;
            }
        }
        return true;
    }
    
    
    //result function for getting the result state of an Action done in toggling Point p
    public static State Result(State pState, Point p) { 
        int[] zero = new int[2];
        State resultState = new State(pState.config, pState.legalActions, pState.cost, pState.dist, pState.total, pState);
        
        //getting zeroth tile position or coordinate
        zero = pState.getZero();
        
        //toggling the action given from point p
        resultState.toggle(p.x, p.y,zero[0],zero[1]);
        resultState.setLegalActions(p.x,p.y);
        return resultState;
    }
    
    
    //method for checking 
    public static boolean checkSolvable(State s){
        int[] list = new int[9];
        int inv=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                list[(i*3)+j]=s.getConfig(i,j);
            }
        }
        
        for(int i=0;i<list.length;i++){
            for(int j=i+1;j<list.length;j++){
                if(list[i]==0)continue;
                
                if(list[j]>list[i]){
                    inv++;
                }
            }
        }
    
        if(inv%2 == 1)return false;
        return true;
    }
    
}
