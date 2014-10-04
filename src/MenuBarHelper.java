import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class MenuBarHelper {
	
	public MenuBar createMenuBar(Stage stage) {
		MenuBar menubar = new MenuBar();
		
		Menu potatoMenu = new Menu("Potato");
		
		menubar.getMenus().addAll(potatoMenu);
		
		return menubar;
	}

}
