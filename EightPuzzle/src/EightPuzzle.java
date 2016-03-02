import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.awt.event.*;

public class EightPuzzle {
	public static void main (String[] args) {
		//mainframe
		final JFrame mainFrame = new JFrame("Eight Puzzle");
		mainFrame.setPreferredSize(new Dimension(400,400));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		//a board class for the game
		final Board game = new Board();
		//button for solve
		JButton solve = new JButton("Solve");
		solve.setPreferredSize(new Dimension(100,40));
		solve.addActionListener(new ActionListener() {
		        public void actionPerformed (ActionEvent e) {
		            int[][] config = new int[3][3];
                    int[][] tp = new int[3][3];
                    for(int i=0;i<3;i++){
                        for(int j=0;j<3;j++){
                            config[i][j]=game.b[i][j].getValue();                            
                            if(config[i][j]==0){
                                if(j>0) tp[i][j-1] = 1;
                                if(j<2) tp[i][j+1] = 1;
                                if(i>0) tp[i-1][j] = 1;
                                if(i<2) tp[i+1][j] = 1;
                            }
                        }
                    }
                    game.setEnabled(false);
                    Solver solution = new Solver(config, tp);
                    //create instance of frame w/ solution board and prev next button
                    solverUI bonus = new solverUI(solution.getList());
                    game.setEnabled(true);
		        }         
		    });
		JButton randomizer = new JButton("Randomize");
		randomizer.setPreferredSize(new Dimension(100,40));    
		randomizer.addActionListener(new ActionListener() {
		    public void actionPerformed (ActionEvent e) {
		        int rands = new Random().nextInt(50);
		        int i=0,r=0,c=0,r2=0,c2=0,temp;
		        for(i=0;i<rands;i++){
		            r = new Random().nextInt(2);
		            c = new Random().nextInt(2);
		            r2 = new Random().nextInt(2);
		            c2 = new Random().nextInt(2);
		            while(r == r2 && c == c2){
		                r2 = new Random().nextInt(2);
		                c2 = new Random().nextInt(2);
		            }
		            temp = game.b[r][c].getValue();
		            game.b[r][c].setValues(r,c,game.b[r2][c2].getValue());
		            game.b[r2][c2].setValues(r2,c2,temp);
		        }
		    }
		});    
		    
		//filechooser and browse button for file configuration input
		final JFileChooser fc = new JFileChooser();		
		JButton file = new JButton("Browse File");
		file.setPreferredSize(new Dimension(200,40));
		file.addActionListener(new ActionListener() { 
		     public void actionPerformed (ActionEvent e) {
		         int returnVal = fc.showOpenDialog(null);   
		         //condition if user has chosen a file
		         if(returnVal == JFileChooser.APPROVE_OPTION) {
		            File file =  fc.getSelectedFile();
		            //reading the file
		            try {
		                //transferring configurations from a file to the Board class named game
		                BufferedReader br = new BufferedReader(new FileReader(file));
		                String s;
		                int j=0;
		                while((s = br.readLine()) != null || j<3) {
		                    String[] row = s.split(" ");
		                    for(int i=0;i<3;i++){
		                        game.b[i][j].setValues(j,i,Integer.parseInt(row[i]));
		                    }
		                    j++;
		                }
		            } catch (Exception ee) {
		                ee.printStackTrace();
		            }
		         }
		     }
		    
		    });
		//button container and setting out the position of UI
		Container buttons = new Container();
		buttons.setLayout(new GridLayout(1,3,0,0));
		buttons.add(solve);
		buttons.add(randomizer);
		buttons.add(file);
		
		Container c = new Container();
		c.setLayout(new BorderLayout());
		c.add(game,BorderLayout.CENTER);
		c.add(buttons,BorderLayout.PAGE_START);
		
		mainFrame.add(c);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
