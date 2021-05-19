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

import java.io.IOException;
import java.time.Instant;
import java.util.List;

public class ResultPageController {

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<Player, String> name;
    @FXML
    private TableColumn<Player, Double> score;

    @FXML
    private void initialize() throws IOException {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        List<Player> players = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .readValue(ResultPageController.class.getResourceAsStream("/data.json"), new TypeReference<List<Player>>() {});


        ObservableList<PlayerResult> observableList = FXCollections.observableArrayList();

        players.stream()
                .filter(a -> a.isResult()==true)
                .sorted().limit(10)
                .forEach(Player ->{
                    PlayerResult playerResult = new PlayerResult();
                    playerResult.setName(Player.getUserName());
                    playerResult.setScore((double) (Player.getNumOfMoves()/Player.getGameDuration()));
                    observableList.add(playerResult);
                });
        tableView.setItems(observableList);
    }

    public void goToMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainPage.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
