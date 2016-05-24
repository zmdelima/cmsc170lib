
public class Tiles extends JButton implements ActionListener {
    int xPos;
    int yPos;
    int value;
    public class Tiles (int tileNum) {
        this.setText(tileNum);
        this.xPos = tileNum/3;
        this.yPos = tileNum%3;
        this.value = 0;
    }
    
    public int getValue() {
        return this.value();
    }
    
    public static actionPerformed (ActionEvent e) {
        
    }
}