package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import util.ControllerHelper;
import org.tinylog.Logger;
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
        Logger.info("New Game button is clicked");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ControllerHelper.loadAndShowFXML(fxmlLoader,"/fxml/SetUsernamePage.fxml",stage);
    }
    /**
     * When Results button is pressed, this function gets called.
     * Function sets stage scene to {@code ResultPage}
     * @throws IOException if {@code FXMLLoader} instance encounters exception.
     */
    public void showResults(ActionEvent actionEvent) throws IOException {
        Logger.info("Results button is clicked");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ControllerHelper.loadAndShowFXML(fxmlLoader,"/fxml/ResultPage.fxml",stage);
    }
}
