import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StageSecondaryHelper {

	public BorderPane createSecondaryStage(Stage stage) {
		BorderPane bp = new BorderPane();
		
		MenuBarHelper mbh = new MenuBarHelper();
		bp.setTop(mbh.createMenuBar(stage));
		ControlPanelHelper cph = new ControlPanelHelper();
		bp.setCenter(cph.createControlPanel());
		SimulationLeft sl = new SimulationLeft();
		bp.setLeft(sl.createSimulation());
		SimulationRight sr = new SimulationRight();
		bp.setRight(sr.createSimulation());
		
		return bp;
	}

}
