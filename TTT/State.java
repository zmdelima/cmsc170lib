class State {
    public int[][] config;
    public int v; //utility
    public int moveX;
    public int moveY;
    public int type; // 0-Min 1-Max 2-Terminal
    public State parent;
    
    public State (int[][] config, int moveX, int moveY, State parent, int type) {
        this.config = new int[3][3];
        this.moveX = moveX;
        this.moveY = moveY;
        
        for (int i=0;i<3;i++) {
            this.config[i][0] = config[i][0];
            this.config[i][1] = config[i][1];
            this.config[i][2] = config[i][2];
        }
        //parent binding   
        this.parent = parent;
        //type initialization
        this.type = type;
        
        //initiating move on configuration
        if (type == 0 && parent != null) this.config[moveX][moveY]=1;
        else if(type == 1 && parent != null) this.config[moveX][moveY]=2;
        
        //reassignment of v and type values as per gamecheck
        int winner = checkWin();
        this.v = 0;
        if (winner == 2) {
            this.v = -1;
            this.type = 2; 
        } 
        else if (winner == 1) {
            this.v = 1;
            this.type = 2;
        }
    }   
    
    public void print () {
        System.out.println("================");
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                System.out.print(this.config[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    //method for checking utility 1=win 0=none -1=lose
    public int checkWin () {
        for (int i=0;i<3;i++) {
            //row checker
            if(this.config[i][0] == this.config[i][1] && this.config[i][0] == this.config[i][2]){
                return this.config[i][0];
            }
            //column checker
            if(this.config[0][i] == this.config[1][i] && this.config[0][i] == this.config[2][i]) {
                return this.config[0][i];
            }
        }
        //diagonal checker
        if (this.config[0][0] == this.config[1][1] && this.config[0][0] == this.config[2][2]) {
            return this.config[0][0];
        }
        else if (this.config[0][2] == this.config[1][1] && this.config[1][1] == this.config[2][0]) {
            return this.config[1][1];
        }
        //if no one wins
        return 0;
    }
    
}