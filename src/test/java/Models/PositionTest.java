package Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    Position position = new Position();
    @Test
    void setRowPosition() {
        position.setRowPosition(5);
        assertEquals(5,position.getRowPosition());
    }

    @Test
    void setColPosition() {
        position.setColPosition(2);
        assertEquals(2,position.getColPosition());
    }

    @Test
    void getRowPosition() {
        position.setRowPosition(10);
        position.setRowPosition(4);
        position.setRowPosition(position.getRowPosition());
        assertEquals(4,position.getRowPosition());
    }

    @Test
    void getColPosition() {
        position.setColPosition(23);
        position.setColPosition(7);
        position.setColPosition(position.getColPosition());
        assertEquals(7,position.getColPosition());
    }
}