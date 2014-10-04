import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StageSecondaryHelper ssh = new StageSecondaryHelper();
        primaryStage.setTitle("Hello World");

        Scene sx = new Scene(ssh.createSecondaryStage(primaryStage), 1000, 650);
        primaryStage.setScene(sx);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
