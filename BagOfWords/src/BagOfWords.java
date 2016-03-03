
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
public class BagOfWords {
	public static LinkedHashMap<String,Integer> dictionary = new LinkedHashMap<String,Integer>();
	public static int total;
	public static int dSize;
	public static void main (String[] args){
		JFrame mainFrame = new JFrame();	
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setPreferredSize(new Dimension(600,530));
		mainFrame.setResizable(false);
		total = 0;
		dSize = 0;
		
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
		JLabel bLabel = new JLabel("Select File:");
		JButton browse = new JButton("Browse File");
		browse.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dictionary.clear();
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
		                		if ( !(dictionary.containsKey(i)) ) {
		                			dictionary.put(i,1);
		                			dSize++;
		                		} 
		                		else if (dictionary.containsKey(i)) {
		                			int temp = dictionary.get(i)+1;
		                			dictionary.put(i,temp);	
		                		}
		                    }
		                    
		                }
		                
		            } catch (Exception ee) {
		                ee.printStackTrace();
		            }
		         }
		         //initiate values
		         bagS.setText(Integer.toString(dSize));
		         bagT.setText(Integer.toString(total));
		         for(String k : dictionary.keySet()){
		        		mod.addRow(new Object[]{ k, dictionary.get(k)});
		         }
			}
		});
		
		
		
		
		Container c1 = new Container();
		c1.setLayout(new BorderLayout());
		c1.add(bLabel,BorderLayout.LINE_START);
		c1.add(browse,BorderLayout.LINE_END);
		
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
		
		
		mainFrame.setLayout(new FlowLayout(1,5,10));
		mainFrame.add(c1);
		mainFrame.add(c2);
		mainFrame.add(c3);
		mainFrame.add(c4);
		mainFrame.pack();
		mainFrame.setVisible(true);
		
	}
}
