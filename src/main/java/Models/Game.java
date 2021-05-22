package Models;

import Controllers.ResultPageController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import org.tinylog.Logger;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
            this.save();
        }
    }

    /**
     * Function gets {@code data.json} file by using Class Loader Mechanism.
     * Then it checks if {@code data.json} has data.
     * If yes, then by using {@code ObjectMapper} it adds all data from the file to a {@code List<Player> playerList}.
     * Then it adds current user to the list.
     * Finally, {@code ObjectWriter} writes the new data to {@code data.json}
     */
    public void save(){
        InputStream data = ResultPageController.class.getResourceAsStream("/data.json");
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            List<Player> playerList = new ArrayList<Player>();
            if(data.available()!=0){
                playerList = objectMapper.readValue(data, new TypeReference<List<Player>>() {
                });
            }
            playerList.add(this.player);
            OutputStream out = new FileOutputStream(ResultPageController.class.getResource("/data.json").getFile());
            ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(out,playerList);
            Logger.info("Game data has been saved");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
