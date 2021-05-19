package Models;

import lombok.Data;

import java.time.Instant;

@Data
public class Player {
    private String userName;
    private int numOfMoves;
    private Position Position;
    private Instant gameStart;
    private Instant gameEnd;
    private int gameDuration;
    private boolean result;

}
