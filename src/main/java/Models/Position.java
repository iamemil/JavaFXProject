package Models;

/**
 * Class to store position of player
 */
public class Position {
    private int row;
    private int col;

    public Position(){
        this.row=1;
        this.col=4;
    }

    public void setRowPosition(int row){
        this.row = row;
    }

    public void setColPosition(int col){
        this.col = col;
    }

    public int getRowPosition(){
        return this.row;
    }

    public int getColPosition(){
        return this.col;
    }
}
