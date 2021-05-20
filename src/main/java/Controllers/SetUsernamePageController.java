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
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GamePage.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            fxmlLoader.<GamePageController>getController().initdata(newUsernameTextField.getText(),scene);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            //log.info("Username is set to {}, loading game scene.", newUsernameTextField.getText());
        }
    }
    /**
     * When New Game button is pressed, this function gets called.
     * Function sets stage scene to {@code MainPage}
     * @throws IOException if {@code FXMLLoader} instance encounters exception.
     */
    public void goToMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainPage.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
