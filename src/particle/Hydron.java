package particle;

import javafx.scene.paint.Color;

/**
 * Created by yusiang on 10/4/14.
 */
public class Hydron extends Particle {
    public Hydron(double x, double y, double v) {
        super(x, y, 2, new Color(1, 0, 0, 0.5), v, 1);
        //this.setFill(Color.DARKRED);
        type = "H+";

    }
}
