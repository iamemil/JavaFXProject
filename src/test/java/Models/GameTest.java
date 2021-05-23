package Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game = new Game();
    @Test
    void checkGameEnd() {
        Position position = new Position(2,5);
        assertTrue(game.checkGameEnd(position));
        position.setColPosition(5);
        assertFalse(game.checkGameEnd(position));
    }

    @Test
    void reset() {
        game.getPlayerPosition().setColPosition(5);
        game.getPlayerPosition().setRowPosition(5);
        game.reset();
        assertEquals(1,game.getPlayerPosition().getRowPosition());
        assertEquals(4,game.getPlayerPosition().getColPosition());
    }

}