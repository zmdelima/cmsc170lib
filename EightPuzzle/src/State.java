//STATE OBJECT CLASS
import java.lang.Math;

public class State { //This state is used to hold the current configuration of a state and its toggled coordinates
    public int[][] config = new int[3][3];
    public int[][] legalActions = new int[3][3];
    public int cost;
    public int dist;
    public int total;  
    public State parent;
    
    //State constructor
    public State (int[][] a, int[][] b, int cost, int dist, int total, State pState) {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
    			this.config[i][j] = a[i][j];
    			this.legalActions[i][j] = b[i][j];        
            }
        }
        this.parent = pState;
        this.cost = cost;
        this.dist = dist;
        this.total = total;
    }
    
    //method for toggling state tile configuration
    public void toggle (int x, int y, int vert, int hori) { //constructor of State class
        int temp;
        //toggle coordinate x,y of the currentState's configuration
        temp = this.config[x][y];
        this.config[x][y] = this.config[vert][hori];
        this.config[vert][hori] = temp;
	    this.upCost();
	    this.setDist();
	    this.setTotal();
        
        
        //printing the state
        /*
        System.out.println("\n=================================\nZERO at "+vert+","+hori);
        System.out.println("TOGGLED at "+x+","+y);
        this.print();
        this.printActions();
        */
    }
    
    //method for getting this.instance's cost
    public int getCost() {
        return this.cost;
    }
    
    public int getDist() {
        return this.dist;
    }
    
    public int getTotal() {
        return this.total;
    }
    //method for new state for increasing its cost by 1
    public void upCost() {
    	this.cost = this.cost + 1;
    }

    //methods for getting state attribute values
    public int getConfig(int i, int j) {
        return this.config[i][j];
    }
    
    public int getLegalAction(int i, int j) {
        return this.legalActions[i][j];
    }
    
    public void setConfig(int i, int j, int val) {
        this.config[i][j] = val;
    }
    
    public void setLegalAction(int i, int j, int val) {
        this.legalActions[i][j] = val;
    }
    
    
    //method for new state of computing its manhattan distance
    public void setDist() {
    	int curr, total2=0, origX=0, origY=0;
    	
    	for(int i=0;i<3;i++){
    		for(int j=0;j<3;j++){
        		curr = this.config[i][j];
        		if (curr==0) continue;
        		origX = (curr-1)/3; //row no. formula number-(adjustments raise while number increases from 0 from initial 0,0)/ dimensions
        		origY = (curr-1)%3; //col no. formula (number+dimensions) % dimensions
        		total2 = total2 + Math.abs(i-origX) + Math.abs(j-origY);
    		}
    	}
    	this.dist=total2;
    }
    
    //method for new state of adding its cost and manhattan distance
    public void setTotal() {
    	this.total = this.cost + this.dist;
    }
    
    //method for setting a State's legalActions from toggling tile(a,b)
    public void setLegalActions(int a, int b) {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
	            this.legalActions[i][j]=0;
            }
        }

        if(b>0) this.legalActions[a][b-1] = 1;
        if(b<2) this.legalActions[a][b+1] = 1;
        if(a>0) this.legalActions[a-1][b] = 1;
        if(a<2) this.legalActions[a+1][b] = 1;
    }
    
    //method for getting the coordinates of the tile with zero value
    public int[] getZero() {
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
    	return zero;
    }
    
    
    
    
    
    
    //printing state values
    public void print() {
        System.out.println("State\n------------------");
        for(int i=0;i<3;i++){
    		for(int j=0;j<3;j++){
    			System.out.print(" "+this.config[i][j]);
    		}
    		System.out.println();
    	}
    }
    
    public void printActions() {
        System.out.println("StateActions\n------------------");
        for(int i=0;i<3;i++){
    		for(int j=0;j<3;j++){
    			System.out.print(" "+this.legalActions[i][j]);
    		}
    		System.out.println();
    	}
    }
}
