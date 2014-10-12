import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuBarHelper {

    public MenuBar createMenuBar(Stage stage) {
        MenuBar menubar = new MenuBar();

        Menu menuHelp = new Menu("Help");
        MenuItem menuItemAbout = new MenuItem("About");
        menuItemAbout.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                StageAboutHelper.createStageAbout();
            }
        });
        MenuItem menuItemInstructions = new MenuItem("Instructions");
        menuItemInstructions.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                StageInstructionsHelper.createStageInstructions();
            }
        });
        MenuItem menuItemCommands = new MenuItem("Commands");
        menuItemCommands.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                StageCommandHelper.createStageCommands();
            }
        });
        menuHelp.getItems().addAll(menuItemAbout, menuItemInstructions, menuItemCommands);

        menubar.getMenus().addAll(menuHelp);

        return menubar;
    }

}
