package Models;

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
    public Cell(int _top, int _bottom,int _left,int _right){
        this.topWall=_top;
        this.bottomWall=_bottom;
        this.leftWall=_left;
        this.rightWall=_right;
    }
}
