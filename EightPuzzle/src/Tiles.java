//This class serves as the buttons/lights
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.lang.Math;

public class Tiles extends JButton implements ActionListener{
	int value;
	int xPos, yPos;
	
	//constructor
	public Tiles (int i, int j) { //constructor
		this.xPos = i;
		this.yPos = j;
	    this.value = (i*3 +j+1);
	    
	    if(i==2 && j==2) this.value=0;
	    
		this.setText(Integer.toString(i*3 +j+1));
		
		if(this.value == 0) {
		    this.setText("");
		}
		
		this.addActionListener(this);
	}
	//getting "tile value" attribute value
	public int getValue () { //function for getting a button's state
		return this.value;
	}
	//getting xPos attribute value
	public int getXPos () {
	    return this.xPos;
	}
	//getting yPos attribute value
	public int getYPos () {
	    return this.yPos;
	}
	
	public void setValues(int i, int j, int value) {
	    this.xPos = i;
		this.yPos = j;
	    this.value = value;
		
		if(value == 0) {
		    this.setText("");
		}
		
		else {
		    this.setText(Integer.toString(this.getValue()));
		}
	}
	
	public void actionPerformed(ActionEvent arg0) { //actionlistener
	    Board.toggle(this.getXPos(),this.getYPos());
	}
}
