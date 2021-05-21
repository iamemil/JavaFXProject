package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;
import util.ControllerHelper;

import java.io.IOException;

/**
 * Controller to manage actions in {@code SetUsernamePage}
 */
public class SetUsernamePageController {

    @FXML
    private TextField newUsernameTextField;

    @FXML
    private Label errorLabel;

    /**
     * When Start button is pressed, this function gets called.
     * Function sets stage scene to {@code GamePage}
     * @throws IOException if {@code FXMLLoader} instance encounters exception.
     */
    public void startGame(ActionEvent actionEvent) throws IOException {
        if (newUsernameTextField.getText().isEmpty()) {
            errorLabel.setText("* Username is empty!");
            Logger.warn("Username field is empty");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GamePage.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Logger.info("Username is set to {}, loading game scene.",newUsernameTextField.getText());
            fxmlLoader.<GamePageController>getController().initdata(newUsernameTextField.getText(),scene);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
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
