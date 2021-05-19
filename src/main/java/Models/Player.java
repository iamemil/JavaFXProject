package Models;

import lombok.Data;

import java.time.Instant;

@Data
public class Player implements Comparable<Player> {
    private String userName;
    private int numOfMoves;
    private Position Position;
    private Instant gameStart;
    private Instant gameEnd;
    private int gameDuration;
    private boolean result;


    @Override
    public int compareTo(Player o) {
        return (o.getNumOfMoves()/o.getGameDuration())-(this.getNumOfMoves()/this.getGameDuration());
    }
}
