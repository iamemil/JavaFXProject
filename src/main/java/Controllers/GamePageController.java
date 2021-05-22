package Controllers;

import Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.*;
import java.time.Instant;
import javafx.stage.Stage;
import org.tinylog.Logger;
import util.ControllerHelper;

import static javafx.scene.shape.StrokeType.INSIDE;

/**
 * Controller to manage actions in {@code GamePage}
 */
public class GamePageController {

    private Game game;

    @FXML
    private GridPane gridBoard;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label numOfMovesLabel;

    @FXML
    private Button gameResetbtn;

    @FXML
    private Button giveUpBtn;

    private Circle playerCircle;

    /**
     * Initializes properties.
     */
    public GamePageController(){
        this.game = new Game();
    }

    /**
     * Function to create blue circle to represent player.
     * @return blue circle with the radius of 20 and the color #0c88f5"
     */
    private Circle createCircle(){
        Circle circle = new Circle();
        circle.setRadius(20);
        circle.setFill(Paint.valueOf("#0c88f5"));
        circle.setStroke(Paint.valueOf("#0c88f5"));
        circle.setStrokeType(INSIDE);
        return circle;
    }

    private void updateCircleLocation(Position position){
        gridBoard.getChildren().remove(playerCircle);
        gridBoard.add(playerCircle,position.getColPosition(),position.getRowPosition());
    }

    /**
     * Function to initialize necessary data
     * @param userName name of the player
     * @param scene scene is passed to be able to add {@code EventFilter} and {@code setOnAction} functions
     */
    public void initdata(String userName,Scene scene) {
        //this.player.setUserName(userName);
        this.game.getPlayer().setUserName(userName);
        usernameLabel.setText("Current user: " + this.game.getPlayer().getUserName());
        numOfMovesLabel.setText("Moves: " + this.game.getPlayer().getNumOfMoves());
        playerCircle = createCircle();
        gridBoard.add(playerCircle,4,1);
        this.game.getPlayer().setGameStart(Instant.now());
        Logger.info("{}'s initial move count is set to 0 and game started at {}",this.game.getPlayer().getUserName(),this.game.getPlayer().getGameStart().toString());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent){
                if(game.getPlayer().getGameEnd()==null){
                    if(keyEvent.getCode() == KeyCode.UP){
                        try {
                            move(Direction.UP);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                    if(keyEvent.getCode() == KeyCode.DOWN){
                        try {
                            move(Direction.DOWN);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                    if(keyEvent.getCode() == KeyCode.LEFT){
                        try {
                            move(Direction.LEFT);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                    if(keyEvent.getCode() == KeyCode.RIGHT){
                        try {
                            move(Direction.RIGHT);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        gameResetbtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent)
            {
                resetGame();
            }
        });

        giveUpBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(game.getPlayer().getGameEnd()==null)
                    Logger.info("{} gave up the game",game.getPlayer().getUserName());
                gameResetbtn.setDisable(true);
                giveUpBtn.setDisable(true);
                game.getPlayer().finishGame();
            }
        });

    }

    /**
     * Function to move player and the blue circle that represents player.
     * Movement is achieved by first checking if next movement is possible,
     * then it checks borders of current cell. If everything is okay until now,
     * function proceeds to set updated Moves count in the GamePage and updates location of blue circle.
     * If user wins, function disables Reset and Give Up buttons.
     * @param direction direction of player
     * @throws JsonProcessingException if {@code finishGame()} function encounters JsonProcessingException.
     */
    private void move(Direction direction) throws JsonProcessingException {
        int newRow = game.getPlayerPosition().getRowPosition() + direction.getDy();
        int newCol = game.getPlayerPosition().getColPosition() + direction.getDx();

        if((newRow >= 0 && newRow <= 6) && (newCol >= 0 && newCol <= 6)){
            Cell currentCell = this.game.getLabyrinth().getCell(this.game.getPlayerPosition().getRowPosition(), this.game.getPlayerPosition().getColPosition());
            boolean possibleMove=false;
            switch (direction){
                case UP -> possibleMove = currentCell.getTopWall() == 0;
                case DOWN -> possibleMove = currentCell.getBottomWall() == 0;
                case LEFT -> possibleMove = currentCell.getLeftWall() == 0;
                case RIGHT -> possibleMove = currentCell.getRightWall() == 0;
            }
            if(possibleMove){
                if(!this.game.getPlayer().checkGameEnd(this.game.getPlayerPosition())){
                    this.game.getPlayerPosition().setRowPosition(newRow);
                    this.game.getPlayerPosition().setColPosition(newCol);
                    this.game.getPlayer().setNumOfMoves(this.game.getPlayer().getNumOfMoves()+1);
                    numOfMovesLabel.setText("Moves: " + this.game.getPlayer().getNumOfMoves());
                    //gridBoard.getChildren().remove(playerCircle);
                    //gridBoard.add(playerCircle,this.playerPosition.getColPosition(),this
                    //        .playerPosition.getRowPosition());
                    this.updateCircleLocation(this.game.getPlayerPosition());
                    Logger.info("{}'s move count is {} and the circle is moved to ({},{})",this.game.getPlayer().getUserName(),this.game.getPlayer().getNumOfMoves(),this.game.getPlayerPosition().getRowPosition(),this.game.getPlayerPosition().getColPosition());
                }
                if(this.game.getPlayer().checkGameEnd(this.game.getPlayerPosition())) {
                    this.game.getPlayer().setResult(true);
                    usernameLabel.setText("Congrats, " + this.game.getPlayer().getUserName() + "! You finished the game !");
                    Logger.info("{} finished the game",this.game.getPlayer().getUserName());
                    gameResetbtn.setDisable(true);
                    giveUpBtn.setDisable(true);
                    this.game.getPlayer().finishGame();
                }
            }
        }

    }

    /**
     * Calls reset function of player
     * Resets usernameLabel to "Current user: {@code username}".
     * Resets numOfMovesLabel to "Moves: 0".
     * Resets blue circle's position by removing it from current place in gridpane and placing in initial place.
     */
    public void resetGame() {
        this.game.getPlayer().reset();
        usernameLabel.setText("Current user: " + this.game.getPlayer().getUserName());
        numOfMovesLabel.setText("Moves: 0");
        gridBoard.getChildren().remove(playerCircle);
        this.game.getPlayerPosition().setRowPosition(1);
        this.game.getPlayerPosition().setColPosition(4);
        gridBoard.add(playerCircle,4,1);
        Logger.info("{}'s move count is set to 0 and the circle is moved to (1,4). Game time is resetted to {}",this.game.getPlayer().getUserName(),this.game.getPlayer().getGameStart());

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