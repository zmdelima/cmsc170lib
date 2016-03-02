//This class serves as container for the game's buttons/tiles
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.lang.Math;

public class Board extends Container{
	public static Tiles[][] b = new Tiles[3][3];
	public Board () { //initiation
		//setting of the lights/buttons of the board
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				b[i][j] = new Tiles(i, j);
				add(b[i][j]);
			}
		}
		setLayout(new GridLayout(3,3,0,0));
	}
	
	//method for swapping tiles
	public static void toggle(int row, int col) {
	    //initialize temporary variables
	    int x1,y1,x2,y2;
	    int zero[] = getZero();
        //assign values to initialized temp variables
        x1=zero[0];
        y1=zero[1];
        x2=b[row][col].getXPos();
        y2=b[row][col].getYPos();
	    //set condition if manhattan distance of button to 0 is 1 then swap tiles
	    //else do nothing
	    if( Math.abs(x1-x2) + Math.abs(y1-y2) == 1) {
	        b[x1][y1].setValues(zero[0],zero[1],b[x2][y2].getValue());
	        b[x2][y2].setValues(b[x2][y2].getXPos(),b[x2][y2].getYPos(),0);
	    }
	    checkGame();
	    return;
	}
	//function for gettingZero
	public static int[] getZero() {
	    int[] zero = {2,2};
	    for(int i=0;i<3;i++){
	        for(int j=0;j<3;j++){
	            if(b[i][j].getValue() == 0){
	              zero[0]=i;
	              zero[1]=j;
	              return zero;
	            }
	        }
	    }
	    return zero;
	}
	//function for GoalTest or victory
	public static void checkGame () { //function for checking if the player already wins
		int temp = 0;
        boolean res = true;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(i==2 && j==2) continue;
                temp = (i*3) + j + 1;
                if(temp != b[i][j].getValue()){
                    res = false;
                }  
            }
        }
        
        if(res){
            JOptionPane.showMessageDialog(new JFrame(),"You Win!","VICTORY!",JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
	}
	
}
