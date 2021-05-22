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
    public void finishGame(){
        if (this.getGameEnd() == null) {
            this.setGameEnd(Instant.now());
            this.saveGame();
        }
    }
    /**
     * Function gets {@code data.json} file by using Class Loader Mechanism.
     * Then it checks if {@code data.json} has data.
     * If yes, then by using {@code ObjectMapper} it adds all data from the file to a {@code List<Player> playerList}.
     * Then it adds current user to the list.
     * Finally, {@code ObjectWriter} writes the new data to {@code data.json}
     */
    public void saveGame(){
        InputStream data = ResultPageController.class.getResourceAsStream("/data.json");
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            List<Player> playerList = new ArrayList<Player>();
            if(data!=null){
                playerList = objectMapper.readValue(data, new TypeReference<List<Player>>() {
                });
            }
            playerList.add(this);
            OutputStream out = new FileOutputStream(ResultPageController.class.getResource("/data.json").getFile());
            ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(out,playerList);
            Logger.info("Game data has been saved");
        }catch (Exception e){
            e.printStackTrace();
        }
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