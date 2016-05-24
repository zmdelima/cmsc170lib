import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Tictactoe implements ActionListener{
	JFrame frame = new JFrame("Tic-Tac-Toe");
	JPanel p;
	JButton[] b = new JButton[9];
	int ctr=0, choice2;
	String letter, choice;
	boolean win = false;
	
	public Tictactoe(){
		p = new JPanel();
		p.setLayout(new GridLayout(3, 3));
		frame.setPreferredSize(new Dimension(300, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		JOptionPane.showMessageDialog(null, "Welcome to Tic-Tac-Toe!");
		choice2 = JOptionPane.showConfirmDialog(null, "Would you like to go first?",null, JOptionPane.YES_NO_OPTION);


		for(int i=0; i<9; i++){
			b[i] = new JButton("");
			b[i].setBackground(Color.WHITE);
			b[i].setFont(new Font("Arial", Font.PLAIN, 40));
			b[i].addActionListener(this);
			p.add(b[i]);
		}

		
		frame.add(p, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	public void checker(){
		//horizontal
		if (b[0].getText() == b[1].getText() && b[1].getText() == b[2].getText() && b[0].getText() != ""){
			this.win = true;
		}else if (b[3].getText() == b[4].getText() && b[4].getText() == b[5].getText() && b[3].getText() != ""){
			this.win = true;
		}else if (b[6].getText() == b[7].getText() && b[7].getText() == b[8].getText() && b[6].getText() != ""){
			this.win = true;
		}

		//vertical
		if (b[0].getText() == b[3].getText() && b[3].getText() == b[6].getText() && b[0].getText() != ""){
			this.win = true;
		}else if (b[1].getText() == b[4].getText() && b[4].getText() == b[7].getText() && b[1].getText() != ""){
			this.win = true;
		}else if (b[2].getText() == b[5].getText() && b[5].getText() == b[8].getText() && b[2].getText() != ""){
			this.win = true;
		}

		//diagonal
		if (b[0].getText() == b[4].getText() && b[4].getText() == b[8].getText() && b[0].getText() != ""){
			this.win = true;
		}else if (b[2].getText() == b[4].getText() && b[4].getText() == b[6].getText() && b[2].getText() != ""){
			this.win = true;
		}

		if (this.win==true){
			if(letter=="X") JOptionPane.showMessageDialog(null, "You won!");
			else if(letter=="O") JOptionPane.showMessageDialog(null, "AI won!");
			else JOptionPane.showMessageDialog(null, "Draw!");
			System.exit(0);
		}
	}

	public void randomTurnAI(){
		int[][] config = new int[3][3];
		int[]	move = new int[2];
		int temp	= 0;
		int x;
		int y;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++) {
				temp  = 0;
				config[i][j]=temp; //AI move
				if(b[(i*3)+j].getText() == "O"){
					temp = 1;
					config[i][j]=temp; //Player move
				}
				else if (b[(i*3)+j].getText() == "X") {
					temp = 2;
					config[i][j]=temp;
				}
				System.out.print(temp+" ");
			}
			System.out.println();
		}
		Solver solver = new Solver(config);
		move = solver.getMove();
		x = move[0];
		y = move[1];
		System.out.println("------MOVED------");
		System.out.println("Move at"+x+","+y);
		if (x >= 0 && y>=0) {
			b[(x*3) + y].doClick();
		}
	}

	public void actionPerformed(ActionEvent e){
		ctr++;
		// if(ctr%2==1) letter="X";
		// else letter="O";

		if(choice2 == JOptionPane.YES_OPTION) {
    		if(ctr%2==1) letter="X";
    		else letter="O";
		} else {
			if(ctr%2==1) letter="O";
    		else letter="X";
		}


		if(choice2 == JOptionPane.NO_OPTION) {
			if(ctr==1){
				randomTurnAI();
			}
		} 

		for(int i=0; i<9; i++){

			if(e.getSource()==b[i]){
				b[i].setText(letter);
				b[i].setEnabled(false);

				if(letter=="X"){
					b[i].setBackground(Color.BLACK);
				} else b[i].setBackground(Color.YELLOW);

			}

			checker();
		}


		if(letter=="X"){
				randomTurnAI();
			}

		
		
	}

	public static void main(String[] args){
		Tictactoe g = new Tictactoe();
	}
}
