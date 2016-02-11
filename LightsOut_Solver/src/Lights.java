//This class serves as the buttons/lights
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Lights extends JButton implements ActionListener{
	public int trigger;
	public int x, y;
	public Lights (int i, int j) { //constructor
		this.off();
		this.x = i;
		this.y = j;
		this.addActionListener(this);
	}
	
	public void off () { //function for setting a button into off state
		this.setBackground(Color.BLACK);
		this.trigger = 0;
	}
	
	public void on () { //function for setting a button into on state
		this.setBackground(Color.YELLOW);
		this.trigger = 1;
	}
	
	public int getStatus () { //function for getting a button's state
		return this.trigger;
	}
	
	public void toggle () { //function for toggling an individual button
		if (this.getStatus() == 1) this.off();
		else this.on();
	}
	
	public void actionPerformed(ActionEvent arg0) { //actionlistener
		Board.toggle(this.x,this.y);
	}
}
