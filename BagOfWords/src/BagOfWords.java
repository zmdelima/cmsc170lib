
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
public class BagOfWords {
	public static LinkedHashMap<String,Integer> spam = new LinkedHashMap<String,Integer>();
	public static LinkedHashMap<String,Integer> ham = new LinkedHashMap<String,Integer>();
	public static int total;
	public static int dSize1;
	public static int dSize2;
	public static double result;
	public static void main (String[] args){
		JFrame mainFrame = new JFrame();	
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setPreferredSize(new Dimension(800,550));
		mainFrame.setResizable(false);
		total = 0;
		dSize1 = 0;
		dSize2 = 0;
		result = 0;
		total = spam.size() + ham.size();
		//JButton and JLabel for Dictionary Size
		JLabel bagSize = new JLabel("Dictionary Size: ");
		final JTextField bagS = new JTextField("");
		bagS.setPreferredSize(new Dimension(50,25));
		bagS.setEditable(false);
		bagS.setBackground(Color.WHITE);
		
		//JButton and JLabel for Total words
		JLabel bagTotal = new JLabel("Total words:  ");
		final JTextField bagT = new JTextField("");
		bagT.setPreferredSize(new Dimension(50,25));
		bagT.setEditable(false);
		bagT.setBackground(Color.WHITE);
		
		String[] colNames = {"Word","Frequency"};
		String[][] data = null;
		
		final JTable table = new JTable(new DefaultTableModel(data,colNames));
		table.setEnabled(false);
		final DefaultTableModel mod = (DefaultTableModel) table.getModel();
		final JFileChooser fc = new JFileChooser();
		
		JButton browseSpam = new JButton("Browse Spam.txt");
		browseSpam.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dSize1 = dSize1 - spam.size();
				for(String k : spam.keySet()){
					total = total - spam.get(k);
				}
				spam.clear();
				mod.setRowCount(0);
				int returnVal = fc.showOpenDialog(null);   
		         //condition if user has chosen a file
		         if(returnVal == JFileChooser.APPROVE_OPTION) {
		            File file =  fc.getSelectedFile();
		            //reading the file
		            try {
		                //transferring configurations from a file to the Board class named game
		                BufferedReader br = new BufferedReader(new FileReader(file));
		                String s;
		                while((s = br.readLine()) != null) {
		                	String[] row = s.split(" ");
		                	for(String i : row){
		                		//System.out.println("\n\nNEW STR\n--------\ncurr str="+i);
		                    	i = i.toLowerCase().replaceAll("[^A-Za-z0-9]","");
		                    	if(i.matches("[w]*")){
		                    		continue;
		                    	}
		                		//System.out.println("\nAFTERCHECK\n-------\ncurr str="+i);
		                		total++;
	                			dSize1++;
		                		if ( !(spam.containsKey(i)) ) {
		                			spam.put(i,1);
		                		} 
		                		else if (spam.containsKey(i)) {
		                			int temp = spam.get(i)+1;
		                			spam.put(i,temp);	
		                		}
		                    }
		                    
		                }
		                
		            } catch (Exception ee) {
		                ee.printStackTrace();
		            }
		         }
		         //initiate values
		         bagS.setText(Integer.toString(dSize1+dSize2));
		         bagT.setText(Integer.toString(total));
		         for(String k : spam.keySet()){
		        		mod.addRow(new Object[]{ k, spam.get(k)});
		         }
			}
		});
		
		final JTable table2 = new JTable(new DefaultTableModel(data,colNames));
		table2.setEnabled(false);
		final DefaultTableModel mod2 = (DefaultTableModel) table2.getModel();
		
		JButton browseHam = new JButton("Browse Ham.txt");
		browseHam.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dSize2 = dSize2 - ham.size();
				for(String k : ham.keySet()){
					total = total - ham.get(k);
				}
				ham.clear();
				mod2.setRowCount(0);
				int returnVal = fc.showOpenDialog(null);   
		         //condition if user has chosen a file
		         if(returnVal == JFileChooser.APPROVE_OPTION) {
		            File file =  fc.getSelectedFile();
		            //reading the file
		            try {
		                //transferring configurations from a file to the Board class named game
		                BufferedReader br = new BufferedReader(new FileReader(file));
		                String s;
		                while((s = br.readLine()) != null) {
		                	String[] row = s.split(" ");
		                	for(String i : row){
		                		//System.out.println("\n\nNEW STR\n--------\ncurr str="+i);
		                    	i = i.toLowerCase().replaceAll("[^A-Za-z0-9]","");
		                    	if(i.matches("[w]*")){
		                    		continue;
		                    	}
		                		//System.out.println("\nAFTERCHECK\n-------\ncurr str="+i);
		                		total++;
		                		dSize2++;
		                		if ( !(ham.containsKey(i)) ) {
		                			ham.put(i,1);
		                		} 
		                		else if (ham.containsKey(i)) {
		                			int temp = ham.get(i)+1;
		                			ham.put(i,temp);	
		                		}
		                    }
		                    
		                }
		                
		            } catch (Exception ee) {
		                ee.printStackTrace();
		            }
		         }
		         //initiate values
		         bagS.setText(Integer.toString(dSize1+dSize2));
		         bagT.setText(Integer.toString(total));
		         for(String k : ham.keySet()){
		        		mod2.addRow(new Object[]{ k, ham.get(k)});
		         }
			}
		});
		
		JButton filter = new JButton("Filter");
		filter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				double upper = 1;
           		double lower = 1;
		                		
				int returnVal = fc.showOpenDialog(null);   
		         //condition if user has chosen a file
		         if(returnVal == JFileChooser.APPROVE_OPTION) {
		            File file =  fc.getSelectedFile();
		            //reading the file
		            try {
		                //transferring configurations from a file to the Board class named game
		                BufferedReader br = new BufferedReader(new FileReader(file));
		                String s;
		                while((s = br.readLine()) != null) {
		                	String[] row = s.split(" ");
		                	for(String i : row){
		                		//System.out.println("\n\nNEW STR\n--------\ncurr str="+i);
		                    	i = i.toLowerCase().replaceAll("[^A-Za-z0-9]","");
		                    	if(i.matches("[w]*")){
		                    		continue;
		                    	}
		                		//System.out.println("\nAFTERCHECK\n-------\ncurr str="+i);
		                		total++;
		                		
		                		if(spam.containsKey(i)){
		                			upper = upper * (spam.get(i)/(double)dSize1);
		                			System.out.println("word="+i+"\nUPPER=="+upper);
		                		} else {
		                			upper = 0;
		                		}
		                    	if(ham.containsKey(i)){
		                    		lower = lower * (ham.get(i)/(double)dSize2);
		                    		System.out.println("word="+i+"\nLOWER=="+lower);
		                    	} else {
		                    		lower = 0;
		                    	}
		                    }
		                    System.out.println("HSize="+ham.size());
		                    System.out.println("SSize="+spam.size());
		                    upper = upper * (dSize2/(double)total);
		                    lower = lower * (dSize1/(double)total);
		                    System.out.println("\nAFTER\nUPPER=="+upper);
		                    lower = lower + upper;
		                    System.out.println("\nAFTER\nLOWER=="+lower);
		                    result = (double)upper/lower;
		                    System.out.println("Result is"+result);
		                    if(result > 0.5) JOptionPane.showMessageDialog(new JFrame(),"Message is Spam!","Results!",JOptionPane.WARNING_MESSAGE);
		                }
		                
		            } catch (Exception ee) {
		                ee.printStackTrace();
		            }
		         }
			}
		});
		
		Container c1 = new Container();
		c1.setLayout(new BorderLayout());
		c1.add(browseSpam,BorderLayout.LINE_END);
		
		Container c2 = new Container();
		c2.setLayout(new BorderLayout());
		c2.add(bagSize,BorderLayout.LINE_START);
		c2.add(bagS,BorderLayout.LINE_END);
		
		Container c3 = new Container();
		c3.setLayout(new BorderLayout());
		c3.add(bagTotal,BorderLayout.LINE_START);
		c3.add(bagT,BorderLayout.LINE_END);
		
		Container c4 = new Container();
		c4.setLayout(new BorderLayout());
		c4.add(new JScrollPane(table),BorderLayout.CENTER);
		
		Container c5 = new Container();
		c5.setLayout(new BorderLayout());
		c5.add(browseHam,BorderLayout.LINE_END);
		c5.add(filter,BorderLayout.LINE_START);
		
		Container c6 = new Container();
		c6.setLayout(new BorderLayout());
		c6.add(new JScrollPane(table2),BorderLayout.CENTER);
		
		
		mainFrame.setLayout(new FlowLayout(1,5,10));
		

		mainFrame.add(c1);
		mainFrame.add(c2);
		mainFrame.add(c3);
		mainFrame.add(c4);
		mainFrame.add(c5);
		mainFrame.add(c6);
		mainFrame.pack();
		mainFrame.setVisible(true);
		
	}
}
