import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    StageSecondaryHelper ssh;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ssh = new StageSecondaryHelper();
        primaryStage.setTitle("Chemister Project");

        Scene sx = new Scene(ssh.createSecondaryStage(primaryStage), 1000, 680); //TODO adjust accordingly
        primaryStage.setScene(sx);
        primaryStage.setResizable(false);
        primaryStage.show();
        //for (int i = 0; i < 1500; i++) ssh.getSimX().run();


    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ssh.getSimX().shutdown();
    }


}
