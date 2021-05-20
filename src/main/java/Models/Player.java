package Models;

import lombok.Data;

import java.time.Instant;

@Data
public class Player implements Comparable<Player> {
    private String userName;
    private int numOfMoves;
    private Instant gameStart;
    private Instant gameEnd;
    private boolean result;


    @Override
    public int compareTo(Player o) {
        return (o.getNumOfMoves()/((int) (o.getGameEnd().getEpochSecond() - o.getGameStart().getEpochSecond())))-(this.getNumOfMoves()/((int) (this.getGameEnd().getEpochSecond() - this.getGameStart().getEpochSecond())));
    }
}
