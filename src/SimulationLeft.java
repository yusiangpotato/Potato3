import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import particle.Particle;

import java.util.ArrayList;

public class SimulationLeft {
    ArrayList<Particle> particleList = new ArrayList<Particle>();
    Pane p;
    int stepn =0;


    Label Lstepn = new Label(stepn+"");
	public Pane createSimulation() {
		p = new Pane();
		p.getChildren().addAll(Lstepn);
		return p;
	}

    public void step(){
        stepn++;
        Lstepn.setText(stepn+"");


    }

}
