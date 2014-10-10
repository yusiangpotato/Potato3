import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StageSecondaryHelper {
    SimX simX;

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
        //Create the SimulationX
        simX = new SimX(sl, sr);

        return bp;
    }

    public SimX getSimX() {
        return simX;
    }
}
