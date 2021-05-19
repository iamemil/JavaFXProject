package Controllers;

import Models.Cell;
import Models.Direction;
import Models.Labyrinth;
import Models.Position;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Instant;

import static javafx.scene.shape.StrokeType.INSIDE;

public class GamePageController {

    private String userName;
    private int numOfMoves;
    private Labyrinth labyrinth;
    private Position userPosition;
    private Instant gameStart;
    private Instant gameEnd;

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
        this.numOfMoves=0;
        this.labyrinth = new Labyrinth();
        this.userPosition = new Position();
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
        this.userName = userName;
        usernameLabel.setText("Current user: " + this.userName);
        numOfMovesLabel.setText("Moves: " + String.valueOf(this.numOfMoves));

        playerCircle = createCircle();
        gridBoard.add(playerCircle,4,1);

        gameStart = Instant.now();

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.UP){
                    move(Direction.UP);
                }
                if(keyEvent.getCode() == KeyCode.DOWN){
                    move(Direction.DOWN);
                }
                if(keyEvent.getCode() == KeyCode.LEFT){
                    move(Direction.LEFT);
                }
                if(keyEvent.getCode() == KeyCode.RIGHT){
                    move(Direction.RIGHT);
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
                System.out.println("Game given up");
                finishGame();
            }
        });

    }


    private void move(Direction direction){
        int newRow = userPosition.getRowPosition() + direction.getDy();
        int newCol = userPosition.getColPosition() + direction.getDx();

        if((newRow >= 0 && newRow <= 6) && (newCol >= 0 && newCol <= 6)){
                Cell currentCell = this.labyrinth.getCell(userPosition.getRowPosition(), userPosition.getColPosition());
                boolean possibleMove=false;
                switch (direction){
                    case UP -> possibleMove = currentCell.getTopWall() == 0 ? true : false;
                    case DOWN -> possibleMove = currentCell.getBottomWall() == 0 ? true : false;
                    case LEFT -> possibleMove = currentCell.getLeftWall() == 0 ? true : false;
                    case RIGHT -> possibleMove = currentCell.getRightWall() == 0 ? true : false;
                }
                if(possibleMove){
                    if(!checkGameEnd()){
                        userPosition.setRowPosition(newRow);
                        userPosition.setColPosition(newCol);
                        this.numOfMoves+=1;
                        numOfMovesLabel.setText("Moves: " + String.valueOf(this.numOfMoves));
                        gridBoard.getChildren().remove(playerCircle);
                        gridBoard.add(playerCircle,this.userPosition.getColPosition(),this
                                .userPosition.getRowPosition());
                    }
                    if(checkGameEnd()) {
                        usernameLabel.setText("Congrats, " + this.userName + "! You won !");
                        finishGame();
                    }
                }
        }

    }

    private boolean checkGameEnd(){
        return userPosition.getRowPosition() == 5 && userPosition.getColPosition() == 2 ? true : false;
    }

    public void resetGame() {
        this.numOfMoves=0;
        numOfMovesLabel.setText("Moves: " + String.valueOf(this.numOfMoves));
        gridBoard.getChildren().remove(playerCircle);
        this.userPosition.setRowPosition(1);
        this.userPosition.setColPosition(4);
        gridBoard.add(playerCircle,4,1);
        gameStart = Instant.now();
        gameEnd = null;
    }

    public void finishGame() {
        if (gameEnd == null) {
        gameEnd = Instant.now();
        System.out.println(gameStart.toString() + " " + gameEnd.toString());
        System.out.println((int) (gameEnd.getEpochSecond() - gameStart.getEpochSecond()));





    }
    }
}
