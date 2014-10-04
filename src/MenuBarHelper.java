import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuBarHelper {
	
	public MenuBar createMenuBar(Stage stage) {
		MenuBar menubar = new MenuBar();
		
		Menu menuHelp = new Menu("Help");
		MenuItem menuItemAbout = new MenuItem("About");
		
		MenuItem menuItemInstructions = new MenuItem("Instructions");
		menuHelp.getItems().addAll(menuItemAbout, menuItemInstructions);
		
		menubar.getMenus().addAll(menuHelp);
		
		return menubar;
	}

}
