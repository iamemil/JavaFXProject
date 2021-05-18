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

public class SetUsernamePageController {

    @FXML
    private TextField newUsernameTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button startGameBtn;


    public void startGame(ActionEvent actionEvent) throws IOException {
        if (newUsernameTextField.getText().isEmpty()) {
            errorLabel.setText("* Username is empty!");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GamePage.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<GamePageController>getController().initdata(newUsernameTextField.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            //log.info("Username is set to {}, loading game scene.", newUsernameTextField.getText());
        }
    }
}
