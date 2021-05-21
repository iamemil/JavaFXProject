package Controllers;

import Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import org.tinylog.Logger;
import util.ControllerHelper;

import static javafx.scene.shape.StrokeType.INSIDE;

/**
 * Controller to manage actions in {@code GamePage}
 */
public class GamePageController {

    final private Player player;
    final private Position playerPosition;
    final private Labyrinth labyrinth;

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
        this.labyrinth = new Labyrinth();
        this.player = new Player();
        this.player.setNumOfMoves(0);
        this.playerPosition = new Position();
        this.player.setResult(false);
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

    /**
     * Function to initialize necessary data
     * @param userName name of the player
     * @param scene scene is passed to be able to add {@code EventFilter} and {@code setOnAction} functions
     */
    public void initdata(String userName,Scene scene) {
        this.player.setUserName(userName);
        usernameLabel.setText("Current user: " + this.player.getUserName());
        numOfMovesLabel.setText("Moves: " + this.player.getNumOfMoves());
        playerCircle = createCircle();
        gridBoard.add(playerCircle,4,1);
        this.player.setGameStart(Instant.now());
        Logger.info("{}'s initial move count is set to 0 and game started at {}",this.player.getUserName(),this.player.getGameStart().toString());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent){
                if(player.getGameEnd()==null){
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
                if(player.getGameEnd()==null)
                   Logger.info("{} gave up the game",player.getUserName());
                gameResetbtn.setDisable(true);
                giveUpBtn.setDisable(true);
                finishGame();
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
        int newRow = playerPosition.getRowPosition() + direction.getDy();
        int newCol = playerPosition.getColPosition() + direction.getDx();

        if((newRow >= 0 && newRow <= 6) && (newCol >= 0 && newCol <= 6)){
                Cell currentCell = this.labyrinth.getCell(this.playerPosition.getRowPosition(), this.playerPosition.getColPosition());
                boolean possibleMove=false;
                switch (direction){
                    case UP -> possibleMove = currentCell.getTopWall() == 0;
                    case DOWN -> possibleMove = currentCell.getBottomWall() == 0;
                    case LEFT -> possibleMove = currentCell.getLeftWall() == 0;
                    case RIGHT -> possibleMove = currentCell.getRightWall() == 0;
                }
                if(possibleMove){
                    if(!checkGameEnd()){
                        this.playerPosition.setRowPosition(newRow);
                        this.playerPosition.setColPosition(newCol);
                        this.player.setNumOfMoves(this.player.getNumOfMoves()+1);
                        numOfMovesLabel.setText("Moves: " + this.player.getNumOfMoves());
                        gridBoard.getChildren().remove(playerCircle);
                        gridBoard.add(playerCircle,this.playerPosition.getColPosition(),this
                                .playerPosition.getRowPosition());
                        Logger.info("{}'s move count is {} and the circle is moved to ({},{})",this.player.getUserName(),this.player.getNumOfMoves(),this.playerPosition.getRowPosition(),this.playerPosition.getColPosition());
                    }
                    if(checkGameEnd()) {
                        this.player.setResult(true);
                        usernameLabel.setText("Congrats, " + this.player.getUserName() + "! You finished the game !");
                        Logger.info("{} finished the game",this.player.getUserName());
                        gameResetbtn.setDisable(true);
                        giveUpBtn.setDisable(true);
                        finishGame();
                    }
                }
        }

    }

    /**
     * Checks if player won the game
     * @return returns 1 if player won, 0 otherwise.
     */
    private boolean checkGameEnd(){
        return this.playerPosition.getRowPosition() == 5 && this.playerPosition.getColPosition() == 2;
    }

    /**
     * Sets player's number of moves to 0.
     * Resets usernameLabel to "Current user: {@code username}".
     * Resets numOfMovesLabel to "Moves: 0".
     * Resets blue circle's position by removing it from current place in gridpane and placing in initial place.
     * Sets player's result to false.
     * Sets player's game start time to current time.
     * Sets player's game end time to {@code null}.
     */
    public void resetGame() {
        this.player.setNumOfMoves(0);
        usernameLabel.setText("Current user: " + this.player.getUserName());
        numOfMovesLabel.setText("Moves: 0");
        gridBoard.getChildren().remove(playerCircle);
        this.playerPosition.setRowPosition(1);
        this.playerPosition.setColPosition(4);
        gridBoard.add(playerCircle,4,1);
        this.player.setResult(false);
        this.player.setGameStart(Instant.now());
        this.player.setGameEnd(null);
        Logger.info("{}'s move count is set to 0 and the circle is moved to (1,4). Game time is resetted to {}",this.player.getUserName(),this.player.getGameStart());

    }

    /**
     * Sets player's game end time current time if it is null.
     * Calls {@code saveGame()} function.
     */
    public void finishGame(){
        if (this.player.getGameEnd() == null) {
        this.player.setGameEnd(Instant.now());
        saveGame();
        }
    }

    /**
     * Function gets {@code data.json} file by using Class Loader Mechanism.
     * Then it checks if {@code data.json} has data.
     * If yes, then by using {@code ObjectMapper} it adds all data from the file to a {@code List<Player> playerList}.
     * Then it adds current user to the list.
     * Finally, {@code ObjectWriter} writes the new data to {@code data.json}
     */
    public void saveGame(){
        File data = new File(GamePageController.class.getClassLoader().getResource("data.json").getFile());
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        try {
            List<Player> playerList = new ArrayList<Player>();
            if(data.length()!=0){
                playerList = objectMapper.readValue(data, new TypeReference<List<Player>>() {
                });
            }
            playerList.add(this.player);
            writer.writeValue(data, playerList);
            Logger.info("Game data has been saved");
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
