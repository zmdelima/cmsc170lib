import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JButton;

public class solButton extends JButton implements ActionListener {
    public int move;
    public solverUI parentUI;
    public solButton (solverUI p, int direction) {
        if (direction == -1) {
            this.setText("PREV");
        } else {
            this.setText("NEXT");
        }
        this.move = direction;
        this.parentUI = p;
        this.setPreferredSize(new Dimension(150,30));
        this.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent ee) { //actionlistener
	    this.parentUI.setBoard(this.getMove());
	}
	
	public int getMove() {
	    return this.move;
	}
	
	public void setStatus(int x){
	    if(x==0){
	        this.setBackground(Color.GRAY);
	        this.setForeground(Color.WHITE);
	        this.setEnabled(false);
	    }
	    else{
	        this.setBackground(new JButton().getBackground());
            this.setForeground(Color.BLACK);
            this.setEnabled(true);
	    }
	}
	
}
