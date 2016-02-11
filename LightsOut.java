
import java.io.*;
//import java.util.Random;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LightsOut {
	public static void main (String[] args) {
		JFrame mainFrame = new JFrame("Lights Out!");
		mainFrame.setPreferredSize(new Dimension(400,400));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		
		Board game = new Board();
		
		JButton solve = new JButton("Solve");
		solve.setPreferredSize(new Dimension(200,40));
		solve.addActionListener(new ActionListener() {
		        public void actionPerformed (ActionEvent e) {
		            
		        }
		    });
		final JFileChooser fc = new JFileChooser();
		
		JButton file = new JButton("Browse File");
		file.setPreferredSize(new Dimension(200,40));
		file.addActionListener(new ActionListener() { 
		     public void actionPerformed (ActionEvent e) {
		         int returnVal = fc.showOpenDialog(null);   
		        
		         if(returnVal == JFileChooser.APPROVE_OPTION) {
		            File file =  fc.getSelectedFile();
		            try {
		                BufferedReader br = new BufferedReader(new FileReader(file));
		                String s;
		                int j=0;
		                while((s = br.readLine()) != null || j<5) {
		                    String[] row = s.split(" ");
		                    for(int i=0;i<5;i++){
		                        Board.set(i,j,Integer.parseInt(row[i]));
		                    }
		                }
		            } catch (Exception ee) {
		                ee.printStackTrace();
		            }
		         }
		     }
		    
		    });
		
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
