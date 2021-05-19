package Controllers;

import Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import java.time.Instant;
import com.fasterxml.jackson.databind.ObjectMapper;
import static javafx.scene.shape.StrokeType.INSIDE;

public class GamePageController {

    private Player player;
    private Labyrinth labyrinth;

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
        this.player.setPosition(new Position());
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
        //this.userName = userName;
        player.setUserName(userName);
        //usernameLabel.setText("Current user: " + this.userName);
        usernameLabel.setText("Current user: " + this.player.getUserName());
        //numOfMovesLabel.setText("Moves: " + String.valueOf(this.numOfMoves));
        numOfMovesLabel.setText("Moves: " + String.valueOf(this.player.getNumOfMoves()));

        playerCircle = createCircle();
        gridBoard.add(playerCircle,4,1);

        //gameStart = Instant.now();
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
                    finishGame();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void move(Direction direction) throws JsonProcessingException {
        //int newRow = userPosition.getRowPosition() + direction.getDy();
        //int newCol = userPosition.getColPosition() + direction.getDx();
        int newRow = this.player.getPosition().getRowPosition() + direction.getDy();
        int newCol = this.player.getPosition().getColPosition() + direction.getDx();


        if((newRow >= 0 && newRow <= 6) && (newCol >= 0 && newCol <= 6)){
                Cell currentCell = this.labyrinth.getCell(this.player.getPosition().getRowPosition(), this.player.getPosition().getColPosition());
                boolean possibleMove=false;
                switch (direction){
                    case UP -> possibleMove = currentCell.getTopWall() == 0 ? true : false;
                    case DOWN -> possibleMove = currentCell.getBottomWall() == 0 ? true : false;
                    case LEFT -> possibleMove = currentCell.getLeftWall() == 0 ? true : false;
                    case RIGHT -> possibleMove = currentCell.getRightWall() == 0 ? true : false;
                }
                if(possibleMove){
                    if(!checkGameEnd()){
                        //userPosition.setRowPosition(newRow);
                        //userPosition.setColPosition(newCol);
                        this.player.getPosition().setRowPosition(newRow);
                        this.player.getPosition().setColPosition(newCol);
                        //this.numOfMoves+=1;
                        this.player.setNumOfMoves(this.player.getNumOfMoves()+1);
                        numOfMovesLabel.setText("Moves: " + String.valueOf(this.player.getNumOfMoves()));
                        gridBoard.getChildren().remove(playerCircle);
                        gridBoard.add(playerCircle,this.player.getPosition().getColPosition(),this
                                .player.getPosition().getRowPosition());
                    }
                    if(checkGameEnd()) {
                        this.player.setResult(true);
                        usernameLabel.setText("Congrats, " + this.player.getUserName() + "! You won !");
                        finishGame();
                    }
                }
        }

    }

    private boolean checkGameEnd(){
        return this.player.getPosition().getRowPosition() == 5 && this.player.getPosition().getColPosition() == 2 ? true : false;
    }

    public void resetGame() {
        this.player.setNumOfMoves(0);;
        usernameLabel.setText("Current user: " + this.player.getUserName());
        numOfMovesLabel.setText("Moves: " + String.valueOf(this.player.getNumOfMoves()));
        gridBoard.getChildren().remove(playerCircle);
        this.player.getPosition().setRowPosition(1);
        this.player.getPosition().setColPosition(4);
        gridBoard.add(playerCircle,4,1);
        this.player.setResult(false);
        this.player.setGameStart(Instant.now());
        this.player.setGameEnd(null);
    }

    public void finishGame() throws JsonProcessingException {
        if (this.player.getGameEnd() == null) {
        this.player.setGameEnd(Instant.now());
        this.player.setGameDuration((int) (this.player.getGameEnd().getEpochSecond() - this.player.getGameStart().getEpochSecond()));
        //System.out.println(this.player.getGameStart().toString() + " " + this.player.getGameEnd().toString());
        //System.out.println((int) (this.player.getGameEnd().getEpochSecond() - this.player.getGameStart().getEpochSecond()));
        //usernameLabel.setText("Current user: " + this.player.getUserName());
        ObjectMapper obj = new ObjectMapper().registerModule(new JavaTimeModule());
        String jsonString = obj.writeValueAsString(this.player);
        System.out.println(jsonString);
        }
    }
}
