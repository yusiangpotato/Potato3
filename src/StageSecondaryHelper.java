import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StageSecondaryHelper {

	public BorderPane createSecondaryStage(Stage stage) {
		BorderPane bp = new BorderPane();
		
		MenuBarHelper mbh = new MenuBarHelper();
		bp.setTop(mbh.createMenuBar(stage));
		//bp.setLeft();
		//bp.setCenter();
		ControlPanelHelper cph = new ControlPanelHelper();
		bp.setRight(cph.createControlPanel());
		
		return bp;
	}

}
