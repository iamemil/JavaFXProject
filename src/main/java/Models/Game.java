package Models;

import lombok.Data;

/**
 * Class to hold information about game
 */
@Data
public class Game {
    private Player player;
    private Position playerPosition;
    private Labyrinth labyrinth;

    public Game(){
        this.player = new Player();
        this.player.setNumOfMoves(0);
        this.player.setResult(false);
        this.playerPosition = new Position();
        this.labyrinth = new Labyrinth();
    }
}
