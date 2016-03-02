import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.util.LinkedList;

public class solverUI extends JFrame{
    public LinkedList<State> list;
    public solTiles[][] solT = new solTiles[3][3];
    public int sNum;
    public solButton next;
    public solButton prev;
    
    public solverUI (LinkedList<State> s) {
        if(s.size()==0){
            JOptionPane.showMessageDialog(new JFrame(),"There are no current Solutions to be displayed!","Solution Empty!",JOptionPane.WARNING_MESSAGE);
            return;
        }
        this.list = s;
        this.sNum = 0;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        Container solC2 = new Container();
        
        solC2.setLayout(new GridLayout(3,3));
        
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                solT[i][j] = new solTiles(s.get(0).getConfig(i,j));    
                solC2.add(solT[i][j]);
            }
        }
        Container btns = new Container();
        
        next = new solButton(this,1);
        prev = new solButton(this,-1);
        prev.setStatus(0);
		
		btns.setLayout(new GridLayout(1,2,0,0));
        btns.add(prev);
        btns.add(next);
        
        Container solC = new Container();
		solC.setLayout(new BorderLayout());
		solC.add(solC2,BorderLayout.CENTER);
		solC.add(btns,BorderLayout.PAGE_START);
        
        this.add(solC);
        this.setResizable(false);
		this.setPreferredSize(new Dimension(300,300));
		this.pack();
		this.setVisible(true);
    }
    
    public void setBoard(int move){
        this.setPanel(move);
        State s = this.list.get(this.getPanel());
        if(this.getPanel()==0){
            getPrev().setStatus(0);
        }
        else if(this.getPanel()==(this.list.size()-1)){
            getNext().setStatus(0);
        }
        else{
            getPrev().setStatus(1);
            getNext().setStatus(1);
        }
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                solT[i][j].setVal(s.getConfig(i,j));
            }
        }
    }
    
    public void setPanel(int move){
        this.sNum = sNum + move;
    }
    
    public int getPanel() {
        return this.sNum;
    }
    
    public solButton getNext() {
        return this.next;
    }
    
    public solButton getPrev() {
        return this.prev;
    }
}
