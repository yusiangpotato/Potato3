import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuBarHelper {
	
	public MenuBar createMenuBar(Stage stage) {
		MenuBar menubar = new MenuBar();
		
		Menu helpMenu = new Menu("Help");
		MenuItem aboutMenuItem = new MenuItem("About");
		MenuItem instructionsMenuItem = new MenuItem("Instructions");
		helpMenu.getItems().addAll(aboutMenuItem, instructionsMenuItem);
		
		menubar.getMenus().addAll(helpMenu);
		
		return menubar;
	}

}
