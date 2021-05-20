package Models;

import lombok.Data;

import java.time.Instant;

/**
 * Class to store player information
 */
@Data
public class Player implements Comparable<Player> {
    private String userName;
    private int numOfMoves;
    private Instant gameStart;
    private Instant gameEnd;
    private boolean result;


    /**
     * Function to compare two players according to numOfMoves/gameDuration logic
     * @param o instance of other player
     * @return result of the comparison
     */
    @Override
    public int compareTo(Player o) {
        return (o.getNumOfMoves()/((int) (o.getGameEnd().getEpochSecond() - o.getGameStart().getEpochSecond())))-(this.getNumOfMoves()/((int) (this.getGameEnd().getEpochSecond() - this.getGameStart().getEpochSecond())));
    }
}
