package Models;

/**
 * Class to store information about borders of cells in {@code GridPane}
 */
public class Cell {

    private int topWall;
    private int bottomWall;
    private int leftWall;
    private int rightWall;

    public Cell(){
        this.topWall=0;
        this.bottomWall=0;
        this.leftWall=0;
        this.rightWall=0;
    }
    /**
     * Constructor to initialize and assign parameters to properties.
     * @param _top If the cell has border on top, then 1 is passed, 0 otherwise.
     * @param _bottom If the cell has border on bottom, then 1 is passed, 0 otherwise.
     * @param _left If the cell has border on left, then 1 is passed, 0 otherwise.
     * @param _right If the cell has border on right, then 1 is passed, 0 otherwise.
     */
    public Cell(int _top, int _bottom,int _left,int _right){
        this.topWall=_top;
        this.bottomWall=_bottom;
        this.leftWall=_left;
        this.rightWall=_right;
    }

    public int getTopWall(){
        return topWall;
    }

    public int getBottomWall() {
        return bottomWall;
    }

    public int getLeftWall(){
        return leftWall;
    }

    public int getRightWall(){
        return rightWall;
    }

}
