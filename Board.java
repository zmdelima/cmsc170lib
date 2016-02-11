import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Board extends Container{
	public static Lights[][] b = new Lights[5][5];
	
	public Board () {
		
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				b[i][j] = new Lights(i, j);
				add(b[i][j]);
			}
		}
		setLayout(new GridLayout(5,5,0,0));
	}
	
	public static void toggle (int x, int y) {
		b[x][y].toggle();
		if(y>0)b[x][y-1].toggle();
        if(y<4)b[x][y+1].toggle();
        if(x>0)b[x-1][y].toggle();
        if(x<4)b[x+1][y].toggle();
		//checkGame();
	}
	
	public static void set (int x, int y, int stat) {
		if(stat == 1){
			b[x][y].on();
		}
		else {
			b[x][y].off();
		}
	}
	
	public static int checkGame () {
		int total = 0;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                total = total + b[i][j].getStatus();  
            }
        }
        if(total==0){
            JOptionPane.showMessageDialog(new JFrame(),"You Win!","VICTORY!",JOptionPane.INFORMATION_MESSAGE);
            //System.exit(0);
        }
		return total;
	}
	
}
