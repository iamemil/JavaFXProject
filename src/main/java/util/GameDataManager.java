package util;

import Controllers.ResultPageController;
import Models.Game;
import Models.Player;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.tinylog.Logger;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Manages game data
 */
public class GameDataManager {

    /**
     * Function checks if program is running as jar or from IDE.
     * According to the options, it calls specific functions.
     */
    public static void save(Game game){
        String protocol = game.getClass().getResource("").getProtocol();
        if(Objects.equals(protocol, "jar")){
            JarSave(game);
        } else if(Objects.equals(protocol, "file")) {
            ideSave(game);
        }
    }
    /**
     * Function gets {@code data.json} file by using Class Loader Mechanism when program is running from IDE.
     * Then it checks if {@code data.json} has data.
     * If yes, then by using {@code ObjectMapper} it adds all data from the file to a {@code List<Player> playerList}.
     * Then it adds current user to the list.
     * Finally, {@code ObjectWriter} writes the new data to {@code data.json}
     */
    private static void ideSave(Game game){
        InputStream data = ResultPageController.class.getResourceAsStream("/data.json");
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            List<Player> playerList = new ArrayList<Player>();
            if(data.available()!=0){
                playerList = objectMapper.readValue(data, new TypeReference<List<Player>>() {
                });
            }
            playerList.add(game.getPlayer());
            OutputStream out = new FileOutputStream(ResultPageController.class.getResource("/data.json").getFile());
            ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(out,playerList);
            Logger.info("Game data has been saved");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Function gets {@code data.json} file by using Class Loader Mechanism program is running as JAR.
     * Then it checks if {@code data.json} has data.
     * If yes, then by using {@code ObjectMapper} it adds all data from the file to a {@code List<Player> playerList}.
     * Then it adds current user to the list.
     * Finally, {@code ObjectWriter} writes the new data to {@code data.json}
     */
    private static void JarSave(Game game){
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            String path = game.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
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
            playerList.add(game.getPlayer());
            OutputStream out = new FileOutputStream(path+"classes/data.json");
            ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(out,playerList);
            Logger.info("Game data has been saved");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
