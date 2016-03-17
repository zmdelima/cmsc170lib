//Author:Zydrick Jan M. Delima
//S.N.:2013-42012
//Date created: Mar 15-17,2016
//Desc: Program with spam filtering system with the use of Naive Bayes classifier algorithm/method

//packages to be used for BagOfWords
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class BagOfWords {
    //global variables needed for the program
    public final static LinkedHashMap<String,Integer> spam = new LinkedHashMap<String,Integer>();
    public final static LinkedHashMap<String,Integer> ham = new LinkedHashMap<String,Integer>();
    public static int total;
    public static int tdSize;
    public static int dSize1;
    public static int dSize2;
    public static int spamLines;
    public static int hamLines;
    public static double result;
    
    public static void main (String[] args){
        JFrame mainFrame = new JFrame("HAM OR SPAM?");    
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(1000,400));
        mainFrame.setResizable(false);
        
        //initialization of variables + filechooser
        final JFileChooser fc = new JFileChooser();
        total     = 0; //for total number of words in all tables
        tdSize    = 0; //for total number of unique/dictionary words in all tables
        dSize1    = 0; //for total number of unique/dicitonary words in the spam table
        dSize2    = 0; //for total number of unique/dicitonary words in the ham table
        result    = 0; //for spam probability computation result variable handler
        spamLines = 0; //for the number of message lines in the spam text file input
        hamLines  = 0; //for the number of message lines in the ham text file input
        
        //components for spam label display/status
        JLabel spamLabel = new JLabel("Total words in Spam:");
        final JTextField spamTotal = new JTextField("");
        spamTotal.setPreferredSize(new Dimension(50,25));
        spamTotal.setEditable(false);
        spamTotal.setBackground(Color.WHITE);
        
        //components for ham label display/status
        JLabel hamLabel = new JLabel("Total words in Ham:");
        final JTextField hamTotal = new JTextField("");
        hamTotal.setPreferredSize(new Dimension(50,25));
        hamTotal.setEditable(false);
        hamTotal.setBackground(Color.WHITE);
        
        //components for input textArea
        JLabel inputLabel = new JLabel("Input:                  ");
        final JTextArea input = new JTextArea("");
        input.setWrapStyleWord(false);
        input.setRows(15);
        input.setText("");
        input.setLineWrap(true);
        input.setPreferredSize(new Dimension(300,100));
        
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
        
        //string arrays for table column components
        String[] colNames = {"Word","Frequency"};
        String[] colNames2 = {"Message","Classification"};
        String[][] data = null;
        
        //spamTable
        final JTable table = new JTable(new DefaultTableModel(data,colNames));
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setPreferredWidth(70);
        final DefaultTableModel mod = (DefaultTableModel) table.getModel();

        //hamTable
        final JTable table2 = new JTable(new DefaultTableModel(data,colNames));
        table2.getColumnModel().getColumn(0).setPreferredWidth(70);
        table2.getColumnModel().getColumn(1).setPreferredWidth(70);
        final DefaultTableModel mod2 = (DefaultTableModel) table2.getModel();
        
        //outputTable
        final JTable output = new JTable(new DefaultTableModel(data,colNames2));
        output.setEnabled(false);
        final DefaultTableModel mod3 = (DefaultTableModel) output.getModel();
        
        //button for spam file input and propagation of spam table from browsed text file
        JButton browseSpam = new JButton("Browse Spam.txt");
        browseSpam.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //initialization of variables
                dSize1 = 0;
                for(String k : spam.keySet()){
                    total = total - spam.get(k);
                    tdSize = tdSize - 1;
                }
                spamLines=0;
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
                                i = i.toLowerCase().replaceAll("[^A-Za-z0-9]","");
                                if(i.matches("[w]*")){
                                    continue;
                                }
                                total = total + 1;
                                dSize1 = dSize1 + 1;
                                if ( !(spam.containsKey(i)) ) {
                                    spam.put(i,1);
                                    if(!(ham.containsKey(i))){
                                        tdSize = tdSize + 1;
                                    }
                                } 
                                else if (spam.containsKey(i)) {
                                    int temp = spam.get(i)+1;
                                    spam.put(i,temp);    
                                }
                            }
                            spamLines++;
                        }
                        
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                 }
                 //initiate values
                 bagS.setText(Integer.toString(tdSize));
                 bagT.setText(Integer.toString(total));
                 spamTotal.setText(Integer.toString(dSize1));
                 for(String k : spam.keySet()){
                        mod.addRow(new Object[]{ k, spam.get(k)});
                 }
            }
        });
        
        //button for spam file input and propagation of ham table from browsed text file
        JButton browseHam = new JButton("Browse Ham.txt");
        browseHam.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //initialization of variables
                dSize2 = 0;
                for(String k : ham.keySet()){
                    total = total - ham.get(k);
                    tdSize = tdSize - 1;
                }
                hamLines=0;
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
                                i = i.toLowerCase().replaceAll("[^A-Za-z0-9]","");
                                if(i.matches("[w]*")){
                                    continue;
                                }
                                total++;
                                dSize2++;
                                if ( !(ham.containsKey(i)) ) {
                                    ham.put(i,1);
                                        if(!(spam.containsKey(i))){
                                            tdSize = tdSize + 1;
                                        }
                                } 
                                else if (ham.containsKey(i)) {
                                    int temp = ham.get(i)+1;
                                    ham.put(i,temp);    
                                }
                            }
                            hamLines++;
                        }
                        
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                 }
                 //initiate values
                 bagS.setText(Integer.toString(tdSize));
                 bagT.setText(Integer.toString(total));
                 hamTotal.setText(Integer.toString(dSize2));
                 for(String k : ham.keySet()){
                        mod2.addRow(new Object[]{ k, ham.get(k)});
                 }
            }
        });
        
        //button to start spam filtering algorithm upon message input in the input textArea
        JButton filter = new JButton("Filter");
        filter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //variables needed
                double upper;
                double lower;
                mod3.setRowCount(0);
                String user_inputs = input.getText();
                  String[] l = user_inputs.split("\n");
            
                if(user_inputs.length() == 0){ 
                    JOptionPane.showMessageDialog(new JFrame(),"There are no lines to identify!","Empty Input Area",JOptionPane.ERROR_MESSAGE);
                    return;
                } 
                
                //getting everyline from the input textArea
                //parsing every word to match every frequency
                //calculate spam probability via every word in the message via naive bayes method   
                for(String s : l) {
                    upper = 1;
                    lower = 1;
                    String[] row = s.split(" ");
                    for(String i : row){
                        i = i.toLowerCase().replaceAll("[^A-Za-z0-9]","");
                        
                        if(i.matches("[w]*")){
                            continue;
                        }
                        
                        //getting P(w|SPAM)
                        if(spam.containsKey(i)){
                            upper = upper * (spam.get(i)/(double)dSize1);
                        } else {
                            upper = 0;
                        }
                        
                        //getting P(w|HAM)
                        if(ham.containsKey(i)){
                            lower = lower * (ham.get(i)/(double)dSize2);
                        } else {
                            lower = 0;
                        }
                    }
                    //final calculation of upper and lower halves
                    //multiplication of probability of spam at upper
                    //combination of (upper and lower) as resulting lower value 
                    upper = upper * (spamLines/(double)(spamLines+hamLines));
                    lower = lower * (hamLines/(double)(spamLines+hamLines));
                    lower = lower + upper;
                    
                    //evaluation of message if it is a HAM or a SPAM
                    if(upper==0){
                        mod3.addRow(new Object[]{s, "HAM"});
                        continue;
                    }
                    result = (double)upper/lower;
                    if(result > 0.5) {
                        mod3.addRow(new Object[]{s, "SPAM"});
                    }
                    else {
                        mod3.addRow(new Object[]{s, "HAM"});
                    }
                }
            }
        });
        
        //container for holding spam label displays/status
        Container spamStatus = new Container();
        spamStatus.setLayout(new FlowLayout(1,1,10));
        spamStatus.add(spamLabel);
        spamStatus.add(spamTotal);
        
        //container for spam table and partition
        Container c1 = new Container();
        c1.setLayout(new GridBagLayout());
        GridBagConstraints config = new GridBagConstraints();
        config.weightx = 0.5;
        config.anchor = GridBagConstraints.PAGE_START;
        config.gridx=0;
        config.gridy=0;
        c1.add(browseSpam,config);
        config.gridx=0;
        config.gridy=1;
        config.weightx=1;
        config.weighty=1;
        config.fill = GridBagConstraints.BOTH;
        c1.add(new JScrollPane(table),config);
        config.weightx=0;
        config.weighty=0;
        config.gridx=0;
        config.gridy=2;
        c1.add(spamStatus,config);
        
        //container for holding ham label displays/status
        Container hamStatus = new Container();
        hamStatus.setLayout(new FlowLayout(1,1,10));
        hamStatus.add(hamLabel);
        hamStatus.add(hamTotal);
        
        //container for ham table and partition
        Container c2 = new Container();
        c2.setLayout(new GridBagLayout());
        config.fill = GridBagConstraints.NONE;
        config.weightx = 0.5;
        config.anchor = GridBagConstraints.PAGE_START;
        config.gridx=0;
        config.gridy=0;
        c2.add(browseHam,config);
        config.gridx=0;
        config.gridy=1;
        config.weightx=1;
        config.weighty=1;
        config.fill = GridBagConstraints.BOTH;
        c2.add(new JScrollPane(table2),config);
        config.weightx=0;
        config.weighty=0;
        config.gridx=0;
        config.gridy=2;
        c2.add(hamStatus,config);
        
        //container for total dictionary size and word count status/display
        Container status = new Container();
        status.setLayout(new FlowLayout());
        status.add(bagSize);
        status.add(bagS);
        status.add(bagTotal);
        status.add(bagT);
                
        //container for input and output section and partition
        Container c3 = new Container();
        c3.setLayout(new GridBagLayout());
        config.weightx=0.5;
        config.gridx=0;
        config.gridy=0;
        c3.add(status,config);
        config.weightx=0;
        config.gridx=0;
        config.gridy=1;
        c3.add(inputLabel,config);
        config.weightx=1;
        config.weighty=1;
        config.fill = GridBagConstraints.BOTH;
        config.gridx=0;
        config.gridy=2;
        c3.add(new JScrollPane(input),config);
        config.weightx=0;
        config.weighty=0;
        config.fill = GridBagConstraints.HORIZONTAL;
        config.gridx=0;
        config.gridy=3;
        c3.add(filter,config);
        config.weightx=0;
        config.weighty=1;
        config.fill = GridBagConstraints.BOTH;
        config.gridx=0;
        config.gridy=4;
        c3.add(new JScrollPane(output),config); 
        
        
        //construction of whole UI
        mainFrame.setLayout(new GridLayout(1,3));
        mainFrame.add(c1);
        mainFrame.add(c2);
        mainFrame.add(c3);
        mainFrame.pack();
        mainFrame.setVisible(true);
        
    }
}
