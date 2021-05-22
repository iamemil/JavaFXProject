package Models;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player1 = new Player();
    Player player2 = new Player();

    @Test
    void compareTo() {
        player1.setNumOfMoves(10);
        player1.setGameStart(Instant.now());
        player1.setGameEnd(Instant.now());
        player2.setNumOfMoves(20);
        player2.setGameStart(Instant.now());
        player2.setGameEnd(Instant.now());

        assertEquals(10, player1.compareTo(player2));
    }
}