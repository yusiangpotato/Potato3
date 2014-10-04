import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    ScheduledExecutorService SimXService;
    @Override
    public void start(Stage primaryStage) throws Exception{
        StageSecondaryHelper ssh = new StageSecondaryHelper();
        primaryStage.setTitle("Hello World");

        Scene sx = new Scene(ssh.createSecondaryStage(primaryStage), 1300, 650);
        primaryStage.setScene(sx);
        primaryStage.setResizable(false);
        primaryStage.show();
        SimXService = Executors.newSingleThreadScheduledExecutor();
        SimXService.scheduleWithFixedDelay(ssh.getSimX(),0,100, TimeUnit.MILLISECONDS);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
