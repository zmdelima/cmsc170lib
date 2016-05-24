import java.util.LinkedList;

class Solver {
    public State root;
    public Solver(int[][] config) {
        //setting of root node
        root = new State(config, -1, -1, null, 1); 
        int currDepth = 0;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int max_v = value(root, currDepth, alpha, beta);
        System.out.println("ALPHA="+alpha+"\nBETA"+beta);
        System.out.println("MAX UTIL "+max_v);
    }
    
    public int[] getMove() {
        int[] moves = new int[2];
        moves[0] = root.moveX;
        moves[1] = root.moveY;
        return moves;
    }
    
    public int value (State s, int currDepth, int alpha, int beta) {
        int value = 0;
        
        if (s.type == 0) { //if minimization
            value = minValue(s, currDepth, alpha, beta);
        }
        else if (s.type == 1) { //if maximization
            value = maxValue(s, currDepth, alpha, beta);
        }
        else { //if terminal and utility
            value = s.v;
        }
        
        return value;
    }
    
    public int maxValue (State s, int currDepth, int alpha, int beta) {
        int v = Integer.MIN_VALUE;
        int tempAlpha = alpha;
        System.out.println("MAX");
        for(State a : successors(s)) {
            a.print();
            System.out.println("VALUE="+v+"MAXVALUE="+value(a, currDepth, alpha, beta));
            
            v = Math.max(v, value(a, currDepth, alpha, beta));
            
            if (v >= beta) {
                System.out.println("Pruned at\n");
                a.print();
                return v;
            }
            
            alpha = Math.max(v, alpha);
            
            if (tempAlpha != alpha && s.parent == null) {
                System.out.println("Moved at"+a.moveX+","+a.moveY);
                s.moveX = a.moveX;
                s.moveY = a.moveY;
            }
            tempAlpha = alpha;
        }
        return v;
    }
    
    public int minValue (State s, int currDepth, int alpha, int beta) {
        int v = Integer.MAX_VALUE;
        System.out.println("MIN");
        for(State a : successors(s)) {
            a.print();
            System.out.println("VALUE="+v+"MINVALUE="+value(a, currDepth, alpha, beta));
            
            v = Math.min(v, value(a, currDepth, alpha, beta));
            
            if (v <= alpha) {
                return v;
            }
            beta = Math.min(v, beta);
        }
        return v;
    }
    
    public LinkedList<State> successors (State s) {
        LinkedList<State> children = new LinkedList<State>();
        int type = (s.type + 1) % 2;
        // System.out.println("curr type ="+type);
        for (int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(s.config[i][j] == 0) {
                    State child = new State(s.config, i, j, s, type);
                    children.addLast(child);
                }
            }
        }
        return children;
    }
}