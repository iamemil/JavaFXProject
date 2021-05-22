package Controllers;

import Models.Player;
import Models.PlayerResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.tinylog.Logger;
import util.ControllerHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controller to manage actions in {@code ResultPage}
 */
public class ResultPageController {

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<Player, String> name;
    @FXML
    private TableColumn<Player, Double> score;

    /**
     * Initializes ResultPage by gathering needed data from data.json.
     * If there is data in data.json, then it is added to {@code ObservableList<PlayerResult> results} list.
     * results list is then passed as {@code tableView.setItems(results)}
     */
    @FXML
    private void initialize() {
        Logger.info("Initializing Results table...");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        String protocol = this.getClass().getResource("").getProtocol();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        List<Player> players = new ArrayList<Player>();
        ObservableList<PlayerResult> results = FXCollections.observableArrayList();
        if(Objects.equals(protocol, "jar")){
            this.jarRead(objectMapper,players,results);
        } else if(Objects.equals(protocol, "file")) {
            this.ideRead(objectMapper,players,results);
        }

    }

    /**
     * Function to fill data when program is running as JAR
     */
    private void jarRead(ObjectMapper objectMapper,List<Player> players,ObservableList<PlayerResult> results){

        try {
            String path = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path = path.substring(0, path.lastIndexOf("/") + 1);
            path = URLDecoder.decode(path, "UTF-8");
            FileInputStream data = new FileInputStream(path+"/classes/data.json");
            if(data.available()!=0){
                players = objectMapper
                        .readValue(data, new TypeReference<List<Player>>() {});
                players.stream()
                        .filter(Player::isResult)
                        .sorted().limit(10)
                        .forEach(Player ->{
                            PlayerResult playerResult = new PlayerResult();
                            playerResult.setName(Player.getUserName());
                            playerResult.setScore((double) (Player.getNumOfMoves()/Integer.max(((int) (Player.getGameEnd().getEpochSecond() - Player.getGameStart().getEpochSecond())),1)));
                            results.add(playerResult);
                        });
            }
            tableView.setItems(results);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Function to fill data when program is running from IDE
     */
    private void ideRead(ObjectMapper objectMapper,List<Player> players,ObservableList<PlayerResult> results){
        InputStream data = ResultPageController.class.getResourceAsStream("/data.json");
        try {
            if(data.available()!=0){
                players = objectMapper
                        .readValue(data, new TypeReference<List<Player>>() {});
                players.stream()
                        .filter(Player::isResult)
                        .sorted().limit(10)
                        .forEach(Player ->{
                            PlayerResult playerResult = new PlayerResult();
                            playerResult.setName(Player.getUserName());
                            playerResult.setScore((double) (Player.getNumOfMoves()/Integer.max(((int) (Player.getGameEnd().getEpochSecond() - Player.getGameStart().getEpochSecond())),1)));
                            results.add(playerResult);
                        });
            }
            tableView.setItems(results);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * When New Game button is pressed, this function gets called.
     * Function sets stage scene to {@code MainPage}
     * @throws IOException if {@code FXMLLoader} instance encounters exception.
     */
    public void goToMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ControllerHelper.loadAndShowFXML(fxmlLoader,"/fxml/MainPage.fxml",stage);
    }
}