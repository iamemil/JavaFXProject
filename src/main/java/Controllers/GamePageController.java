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
import javafx.scene.Parent;
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

import static javafx.scene.shape.StrokeType.INSIDE;

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

    public GamePageController(){
        this.labyrinth = new Labyrinth();
        this.player = new Player();
        this.player.setNumOfMoves(0);
        this.playerPosition = new Position();
        this.player.setResult(false);
    }

    private Circle createCircle(){
        Circle circle = new Circle();
        circle.setRadius(20);
        circle.setFill(Paint.valueOf("#0c88f5"));
        circle.setStroke(Paint.valueOf("#0c88f5"));
        circle.setStrokeType(INSIDE);
        return circle;
    }
    public void initdata(String userName,Scene scene) {
        player.setUserName(userName);
        usernameLabel.setText("Current user: " + this.player.getUserName());
        numOfMovesLabel.setText("Moves: " + this.player.getNumOfMoves());

        playerCircle = createCircle();
        gridBoard.add(playerCircle,4,1);
        this.player.setGameStart(Instant.now());

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent){
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
        });

        gameResetbtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent)
            {
                System.out.println("Game resetted");
                resetGame();
            }
        });

        giveUpBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(player.getGameEnd()==null)
                    System.out.println("Game given up");
                try {
                    gameResetbtn.setDisable(true);
                    giveUpBtn.setDisable(true);
                    finishGame();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });

    }


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
                    }
                    if(checkGameEnd()) {
                        this.player.setResult(true);
                        usernameLabel.setText("Congrats, " + this.player.getUserName() + "! You won !");
                        gameResetbtn.setDisable(true);
                        giveUpBtn.setDisable(true);
                        finishGame();
                    }
                }
        }

    }

    private boolean checkGameEnd(){
        return this.playerPosition.getRowPosition() == 5 && this.playerPosition.getColPosition() == 2;
    }

    public void resetGame() {
        this.player.setNumOfMoves(0);
        usernameLabel.setText("Current user: " + this.player.getUserName());
        numOfMovesLabel.setText("Moves: " + this.player.getNumOfMoves());
        gridBoard.getChildren().remove(playerCircle);
        this.playerPosition.setRowPosition(1);
        this.playerPosition.setColPosition(4);
        gridBoard.add(playerCircle,4,1);
        this.player.setResult(false);
        this.player.setGameStart(Instant.now());
        this.player.setGameEnd(null);
    }

    public void finishGame() throws JsonProcessingException {
        if (this.player.getGameEnd() == null) {
        this.player.setGameEnd(Instant.now());
        ObjectMapper obj = new ObjectMapper().registerModule(new JavaTimeModule());
        String jsonString = obj.writeValueAsString(this.player);
        System.out.println(jsonString);
        saveGame();
        }
    }

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
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void goToMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainPage.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
