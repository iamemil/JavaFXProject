
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tinylog.Logger;

public class LabyrinthApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        String appName="Labyrinth Game";
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
        stage.setTitle(appName);
        Logger.info("Application title has been set to {}",appName);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
