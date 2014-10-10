package particle;

import javafx.scene.paint.Color;

/**
 * Created by yusiang on 10/4/14.
 */
public class Hydron extends Particle {
    public Hydron(int v) {
        super(1, Color.RED, v);
        //this.setFill(Color.DARKRED);
        type = "H+";

    }
}
