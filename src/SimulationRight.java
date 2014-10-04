import javafx.scene.layout.Pane;
import particle.Particle;

import java.util.ArrayList;

public class SimulationRight {
    ArrayList<Particle> particleList = new ArrayList<Particle>();
	public Pane createSimulation() {
		Pane p = new Pane();
        p.setMinSize(500f,650f);
        p.setPrefSize(500f,650f);
        p.setMaxSize(500f,650f);

		return p;
	}
    public void step(){


    }
}
