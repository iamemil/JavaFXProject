package Controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GamePageController {

    private String userName;


    @FXML
    private GridPane board;

    public void initdata(String userName) {
        this.userName = userName;
        //usernameLabel.setText("Current user: " + this.userName);
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (Pane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        System.out.printf("Click on square (%d,%d)\n", row, col);
        //model.move(row, col);
    }

}
