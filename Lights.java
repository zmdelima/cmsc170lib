import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Lights extends JButton implements ActionListener{
	private int trigger;
	public int x, y;
	public Lights (int i, int j) {
		this.off();
		this.x = i;
		this.y = j;
		this.addActionListener(this);
	}
	
	public void off () {
		this.setBackground(Color.BLACK);
		this.trigger = 0;
	}
	
	public void on () {
		this.setBackground(Color.YELLOW);
		this.trigger = 1;
	}
	
	public int getStatus () {
		return this.trigger;
	}
	
	public void toggle () {
		if (this.getStatus() == 1) this.off();
		else this.on();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Board.toggle(this.x,this.y);
	}
}
