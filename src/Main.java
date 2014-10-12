import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    ScheduledExecutorService SimXService;

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageSecondaryHelper ssh = new StageSecondaryHelper();
        primaryStage.setTitle("Chemister Project");

        Scene sx = new Scene(ssh.createSecondaryStage(primaryStage), 1300, 675); //TODO adjust accordingly
        primaryStage.setScene(sx);
        primaryStage.setResizable(false);
        primaryStage.show();
        //for (int i = 0; i < 1500; i++) ssh.getSimX().run();
        SimXService = Executors.newSingleThreadScheduledExecutor();
        SimXService.scheduleWithFixedDelay(ssh.getSimX(), 0, 25, TimeUnit.MILLISECONDS);

    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        SimXService.shutdown();
    }


}
