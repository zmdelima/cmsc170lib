import java.io.*;
import java.awt.*;
import javax.swing.*;
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
		solve.setPreferredSize(new Dimension(200,40));
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
                            } else {
                                tp[i][j]=0;
                            }
                        }
                        System.out.println();    
                    }
                    game.setEnabled(false);
                    Solver solution = new Solver(config, tp);
                    stateList s = solution.getList();
                    //create instance of frame w/ solution board and prev next button
                    game.setEnabled(true);
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
		buttons.setLayout(new GridLayout(1,2,0,0));
		buttons.add(solve);
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
