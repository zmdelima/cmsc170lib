import javax.swing.*;
import java.util.LinkedList;


public class ttt {
    public Tiles[][] tiles = new Tiles[3][3];
    
    public static void main (String args[]) {
        JFrame mainFrame = new JFrame("Tic-Tac-Toe");
        Container board = new Container();
        
        board.setLayout(new GridLayout(3,3));
        for (i=0;i<9;i++) {
            Tiles t = new Tiles(i);
            board.add(t);
        }        
    }
    
    public int checkGame() {
        //row checking
        if(tiles[0][0].getValue() == tiles[0][1].getValue() && tiles[0][0].getValue() == tiles[0][2].getValue()) return tiles[0][0].getValue();
        else if(tiles[1][0].getValue() == tiles[1[1].getValue() && tiles[1][0].getValue() == tiles[1][2].getValue()) return tiles[1][0].getValue();
        else if(tiles[2][0].getValue() == tiles[2][1].getValue() && tiles[2][0].getValue() == tiles[2][2].getValue()) return tiles[2][0].getValue();
        //column checking
        else if(tiles[0][0].getValue() == tiles[1][0].getValue() && tiles[0][0].getValue() == tiles[2][0].getValue()) return tiles[0][0].getValue();
        else if(tiles[0][1].getValue() == tiles[1][1].getValue() && tiles[0][1].getValue() == tiles[2][1].getValue()) return tiles[0][1].getValue();
        else if(tiles[0][2].getValue() == tiles[1][2].getValue() && tiles[0][2].getValue() == tiles[2][2].getValue()) return tiles[0][2].getValue();
        //diagonal checking
        else if(tiles[0][0].getValue() == tiles[1][1].getValue() && tiles[0][0].getValue() == tiles[2][2].getValue()) return tiles[0][0].getValue();
        else if(tiles[2][0].getValue() == tiles[1][1].getValue() && tiles[2][0].getValue() == tiles[0][2].getValue()) return tiles[0][0].getValue();
        else return -1;
    }
}
