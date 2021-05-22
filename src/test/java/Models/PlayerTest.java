package Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player = new Player();
    Position position = new Position(2,5);
    @Test
    void checkGameEnd() {
        assertEquals(true,player.checkGameEnd(position));
    }
}