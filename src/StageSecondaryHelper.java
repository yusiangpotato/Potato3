import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StageSecondaryHelper {
    SimX simX;

    public BorderPane createSecondaryStage(Stage stage) {
        BorderPane bp = new BorderPane();

        MenuBarHelper mbh = new MenuBarHelper();
        bp.setTop(mbh.createMenuBar(stage));

        SimulationLeft sl = new SimulationLeft();
        bp.setLeft(sl.createSimulation());
        SimulationRight sr = new SimulationRight();
        bp.setRight(sr.createSimulation());
        ControlPanelHelper cph = new ControlPanelHelper();
        //Create the SimulationController
        simX = new SimX(cph, sl, sr);

        bp.setCenter(cph.createControlPanel(simX));

        return bp;
    }

    public SimX getSimX() {
        return simX;
    }
}
