package Models;

import java.time.Instant;
import lombok.Data;
/**
 * Class to hold player information
 */
@Data
public class Player implements Comparable<Player> {
    private String userName;
    private int numOfMoves;
    private Instant gameStart;
    private Instant gameEnd;
    private boolean result;

    /**
     * Resets player's values.
     * Sets player's number of moves to 0.
     * Sets player's result to false.
     * Sets player's game start time to current time.
     * Sets player's game end time to {@code null}.
     */
    public void reset(){
        this.setNumOfMoves(0);
        this.setResult(false);
        this.setGameStart(Instant.now());
        this.setGameEnd(null);
    }

    /**
     * Function to compare two players according to numOfMoves/gameDuration logic
     * @param o instance of other player
     * @return result of the comparison
     */
    @Override
    public int compareTo(Player o) {
        return (o.getNumOfMoves()/ Integer.max(((int) (o.getGameEnd().getEpochSecond() - o.getGameStart().getEpochSecond())),1) )-(this.getNumOfMoves()/ Integer.max(((int) (this.getGameEnd().getEpochSecond() - this.getGameStart().getEpochSecond())),1) );
    }
}