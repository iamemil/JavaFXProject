package Models;

import lombok.Data;
import util.GameDataManager;
import java.time.Instant;
/**
 * Class to hold information about game
 */
@Data
public class Game {
    private Player player;
    private Position playerPosition;
    private Labyrinth labyrinth;

    /**
     * Sets properties to needed initial values
     */
    public Game(){
        this.player = new Player();
        this.player.setNumOfMoves(0);
        this.player.setResult(false);
        this.playerPosition = new Position();
        this.labyrinth = new Labyrinth();
    }

    /**
     * Resets player's values.
     * Calls player's {@code reset()} function.
     * Sets player's position to (1,4).
     */
    public void reset(){
        this.player.reset();
        this.getPlayerPosition().setRowPosition(1);
        this.getPlayerPosition().setColPosition(4);
    }

    /**
     * Checks if player won the game
     * @return returns 1 if player won, 0 otherwise.
     */
    public boolean checkGameEnd(Position playerPosition){
        return playerPosition.getRowPosition() == 5 && playerPosition.getColPosition() == 2;
    }

    /**
     * Sets player's game end time current time if it is null.
     * Calls {@code saveGame()} function.
     */
    public void finish(){
        if (this.player.getGameEnd() == null) {
            this.player.setGameEnd(Instant.now());
            GameDataManager.save(this);
        }
    }
}
