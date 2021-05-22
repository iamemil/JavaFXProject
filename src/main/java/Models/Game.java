package Models;

import Controllers.ResultPageController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import lombok.extern.java.Log;
import org.tinylog.Logger;

import java.io.*;
import java.net.URLDecoder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        String protocol = this.getClass().getResource("").getProtocol();
        if(Objects.equals(protocol, "jar")){
            this.JarSave();
        } else if(Objects.equals(protocol, "file")) {
            this.ideSave();
        }
    }
    /**
     * Function to save data when program is running from IDE
     */
    private void ideSave(){
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

    /**
     * Function to fill data when program is running as JAR
     */
    private void JarSave(){
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            String path = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path = path.substring(0, path.lastIndexOf("/") + 1);
            path = URLDecoder.decode(path, "UTF-8");
            FileInputStream data = new FileInputStream(path+"classes/data.json");
            Logger.info(data);
            BufferedReader in = new BufferedReader(new InputStreamReader(data));
            List<Player> playerList = new ArrayList<Player>();
            if(data.available()!=0){
                playerList = objectMapper.readValue(in, new TypeReference<List<Player>>() {
                });
            }
            playerList.add(this.player);
            OutputStream out = new FileOutputStream(path+"classes/data.json");
            ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(out,playerList);
            Logger.info("Game data has been saved");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
