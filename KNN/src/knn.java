import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class knn {
    public static int dimensions;
    public static int totalPoints;
    public static int k;
    public static HashMap<Integer,ArrayList<Double>> pointMaps = new HashMap<Integer,ArrayList<Double>>();
    public static void main (String[] args) {
        //mainFrame for simple UI
        JFrame mainFrame = new JFrame("k-Nearest Neighbors");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(1000,400));
        mainFrame.setResizable(false);
        
        //initialization of variables + filechooser
        final JFileChooser fc = new JFileChooser();
        k = 0;
        dimensions = 0;
        totalPoints = 0;
        
        //components for input textArea
        JLabel inputLabel = new JLabel("Input:                                            ");
        final JTextArea input = new JTextArea("");
        input.setWrapStyleWord(false);
        input.setRows(15);
        input.setText("");
        input.setLineWrap(true);
        input.setPreferredSize(new Dimension(300,100));
        
        //button for browsing training.txt file
        JButton browseTraining = new JButton("Browse training.txt");
        browseTraining.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int returnVal = fc.showOpenDialog(null);
                int counter = 0;
                int prevlength=-1;
                int currlength=-1;
                int i=0;
                pointMaps.clear();
                 //condition if user has chosen a file
                 if(returnVal == JFileChooser.APPROVE_OPTION) {
                    File file =  fc.getSelectedFile();
                    //reading the file 
                    try {
                        //transferring configurations from a file to hashmap
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String s;
                        k = Integer.parseInt(br.readLine());
                        while((s = br.readLine()) != null) {
                            String[] row = s.split(" ");
                            ArrayList<Double> list = new ArrayList<Double>();
                            currlength=row.length;
                            
                            for(i=0;i<row.length;i++){
                                list.add(i,Double.parseDouble(row[i]));
                            }
                            
                            pointMaps.put(counter,list); 
                            counter = counter + 1;
                            
                            if(prevlength!=currlength && prevlength!=-1){
                            //throw error regarding inconsistent point dimensions
                                pointMaps.clear();
                                //show dialog for user interface upon error
                                JOptionPane.showMessageDialog(new JFrame(),"Inconsistent dimension at line "+(counter+1)+"!","Different point dimensions!",JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            prevlength=row.length;
                        }
                        dimensions=currlength;
                        totalPoints=counter;
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                 }
            
            }
        });
        
        //button for classification of input at textarea and training.txt
        JButton classify = new JButton("Classify");
        classify.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int i=0,j=0,k=0,type=0, l=0;
                double temp = 0;
                HashMap<Integer,Integer> tList = new HashMap<Integer,Integer>();
                String[] row = (input.getText()).split("\n");
                //input per line
                for(i=0;i<row.length;i++){
                    //variables needed for dynamic processing of points with training.txt classifications
                    String[] col = row[i].split(" ");
                    ArrayList<ArrayList<Double>> results = new ArrayList<ArrayList<Double>>();
                    
                    if(col.length!=(dimensions-1)){
                        //input error!
                        JOptionPane.showMessageDialog(new JFrame(),"Inconsistent dimension at line "+(i+1)+" in the input area and on training.txt!","Mismatch dimensions!",JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    
                    //printing for points
                    
                    
                    //mainloop for processing distance of current input line to each point in training.txt
                    for(j=0;j<totalPoints;j++){
                        ArrayList<Double> d_class = new ArrayList<Double>();
                        temp = 0;
                        for(k=0;k<(dimensions-1);k++){
                            temp = temp + Math.pow( (Double.parseDouble(col[k]) - (double) (pointMaps.get(j)).get(k)), 2);
                            
                        }
                        temp= Math.sqrt(temp);
                        //System.out.println("result["+j+"] ="+temp+"\t\t"+"class:"+((pointMaps.get(j)).get(k).intValue())+".");
                        for(k=0;k<results.size();k++){
                            if(temp < (double) (results.get(k).get(0)))break;
                        }
                        d_class.add(0, temp);
                        d_class.add(1, (double) (pointMaps.get(j)).get(dimensions-1));
                        for(l=0;l<(dimensions-1);l++){
                            d_class.add(l+2, (double) (pointMaps.get(j)).get(l));
                        }
                        results.add(k, d_class);
                    }
                    
                    //printing of result
                    System.out.print("\nNearest neighbors of (");
                    for(j=0;j<(dimensions-1);j++){
                        System.out.print(col[j]);
                        if(j+1 == dimensions-1)break;
                        System.out.print(", ");
                    }
                    System.out.print("):");
                    for(j=0;j<k;j++){
                        System.out.print(" (");
                        for(l=0;l<(dimensions-1);l++){
                            System.out.print((results.get(j)).get(l+2));
                            if(l+1 == dimensions-1)break;
                            System.out.print(", ");
                        }
                        System.out.print(")");
                    }
                    //getting of classification number
                    type = getKNN(results);
                    
                    //printing of classification number
                    System.out.print("\nClass of:(");
                    for(j=0;j<(dimensions-1);j++){
                        System.out.print(col[j]);
                        if(j+1 == dimensions-1)break;
                        System.out.print(", ");
                    }
                    System.out.print("):"+type);
                }
            }
            
            //function for getting the nearest neighbor
            public int getKNN(ArrayList<ArrayList<Double>> list){
                int temp=0, i=0,j=0,max=0;
                HashMap<Integer,Integer> nearest = new HashMap<Integer,Integer>();
                    for(i=0;i<k;i++){
                        temp =  (list.get(i)).get(1).intValue();
                        
                        if (!(nearest.containsKey(temp))) {
                            nearest.put(temp, 1);
                        }
                        else {
                            nearest.put(temp, nearest.get(temp) + 1);
                        }
                    }

                    for(int s : nearest.keySet()){
                        if(max <= nearest.get(s)){
                            temp = s;
                            max = nearest.get(s);
                        }
                    }
                return temp;
            }
            
        });
        
        mainFrame.add(browseTraining);
        mainFrame.add(classify);
        mainFrame.add(input);
        
        mainFrame.setLayout(new GridLayout(1,3));
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
