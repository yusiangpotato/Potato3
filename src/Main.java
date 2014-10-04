import java.util.List;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = new Pane();
        primaryStage.setTitle("Hello World");
        root.getChildren().add(new Circle(10,10,100));


        Scene sx = new Scene(root, 1000, 650);
        primaryStage.setScene(sx);
        primaryStage.setResizable(false);
        MenuBarHelper mbh = new MenuBarHelper();
        root.getChildren().add(mbh.createMenuBar(primaryStage));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
