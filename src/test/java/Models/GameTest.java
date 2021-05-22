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

}