package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller to manage actions in {@code MainPage}
 */
public class MainPageController {


    /**
     * When New Game button is pressed, this function gets called.
     * Function sets stage scene to {@code SetUsernamePage}
     * @throws IOException if {@code FXMLLoader} instance encounters exception.
     */
    public void startNewGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SetUsernamePage.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * When Results button is pressed, this function gets called.
     * Function sets stage scene to {@code ResultPage}
     * @throws IOException if {@code FXMLLoader} instance encounters exception.
     */
    public void showResults(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ResultPage.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
