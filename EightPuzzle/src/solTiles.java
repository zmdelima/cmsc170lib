//class for solverUI's Tiles
//packages needed
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class solTiles extends JButton{
    //tile value
    int val;
    
    //class constructor
    public solTiles (int value) {
        
        this.val = value;
        if(value == 0) {
		    this.setText("");
		    this.setBackground(Color.BLACK);
		    this.setForeground(Color.WHITE);

		}
		else {
		    this.setText(Integer.toString(this.getVal()));    
	    	this.setBackground(new JButton().getBackground());
		    this.setForeground(Color.BLACK);
		}
        this.setEnabled(false);
    }
    
    //getting tile's value
    public int getVal() {
        return this.val;
    }
    
    //setting a tile's value and interface properties
    public void setVal(int value){
        this.val = value;
        if(value == 0) {
		    this.setText("");
		    this.setBackground(Color.BLACK);
		    this.setForeground(Color.WHITE);

		}
		
		else {
		    this.setText(Integer.toString(this.getVal()));    
	    	this.setBackground(new JButton().getBackground());
		    this.setForeground(Color.BLACK);
		}
    }
}
