package Models;

public class Labyrinth {
    private Cell[][] cells;

    public Labyrinth(){
        //Row 0
        cells = new Cell[7][7];
        this.cells[0][0] = new Cell(1,0,1,1);
        this.cells[0][1] = new Cell(1,0,1,0);
        this.cells[0][2] = new Cell(1,1,0,0);
        this.cells[0][3] = new Cell(1,0,0,1);
        this.cells[0][4] = new Cell(1,0,1,0);
        this.cells[0][5] = new Cell(1,0,0,0);
        this.cells[0][6] = new Cell(1,1,0,1);

        // Row 1
        this.cells[1][0] = new Cell(0,0,1,0);
        this.cells[1][1] = new Cell();
        this.cells[1][2] = new Cell(1,0,0,0);
        this.cells[1][3] = new Cell();
        this.cells[1][4] = new Cell();
        this.cells[1][5] = new Cell();
        this.cells[1][6] = new Cell(1,0,0,1);

        // Row 2
        this.cells[2][0] = new Cell(0,0,1,0);
        this.cells[2][1] = new Cell(0,1,0,0);
        this.cells[2][2] = new Cell(0,0,0,1);
        this.cells[2][3] = new Cell(0,0,1,0);
        this.cells[2][4] = new Cell();
        this.cells[2][5] = new Cell(0,0,0,1);
        this.cells[2][6] = new Cell(0,0,1,1);

        // Row 3
        this.cells[3][0] = new Cell(0,0,1,0);
        this.cells[3][1] = new Cell(1,0,0,0);
        this.cells[3][2] = new Cell();
        this.cells[3][3] = new Cell(0,1,0,1);
        this.cells[3][4] = new Cell(0,0,1,1);
        this.cells[3][5] = new Cell(0,0,1,0);
        this.cells[3][6] = new Cell(0,1,0,1);

        // Row 4
        this.cells[4][0] = new Cell(0,1,1,0);
        this.cells[4][1] = new Cell();
        this.cells[4][2] = new Cell();
        this.cells[4][3] = new Cell(1,0,0,0);
        this.cells[4][4] = new Cell(0,1,0,0);
        this.cells[4][5] = new Cell();
        this.cells[4][6] = new Cell(1,0,0,1);

        // Row 5
        this.cells[5][0] = new Cell(1,0,1,0);
        this.cells[5][1] = new Cell(0,0,0,1);
        this.cells[5][2] = new Cell(0,1,1,1);
        this.cells[5][3] = new Cell(0,0,1,0);
        this.cells[5][4] = new Cell(1,0,0,0);
        this.cells[5][5] = new Cell();
        this.cells[5][6] = new Cell(0,0,0,1);

        // Row 6
        this.cells[6][0] = new Cell(0,1,1,0);
        this.cells[6][1] = new Cell(0,1,0,0);
        this.cells[6][2] = new Cell(1,1,0,0);
        this.cells[6][3] = new Cell(0,1,0,1);
        this.cells[6][4] = new Cell(0,1,1,0);
        this.cells[6][5] = new Cell(0,1,0,1);
        this.cells[6][6] = new Cell(0,1,1,1);
    }

    public Cell getCell(int row, int col){
        return this.cells[row][col];
    }

}
