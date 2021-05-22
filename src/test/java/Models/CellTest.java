package Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    Cell cell = new Cell(1,0,1,1);
    Cell cell2 = new Cell();
    @Test
    void getTopWall() {
        assertEquals(1,cell.getTopWall());
        assertEquals(0,cell2.getTopWall());
    }

    @Test
    void getBottomWall() {
        assertEquals(0,cell.getBottomWall());
        assertEquals(0,cell2.getBottomWall());
    }

    @Test
    void getLeftWall() {
        assertEquals(1,cell.getLeftWall());
        assertEquals(0,cell2.getLeftWall());
    }

    @Test
    void getRightWall() {
        assertEquals(1,cell.getRightWall());
        assertEquals(0,cell2.getRightWall());
    }
}