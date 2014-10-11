package particle;

import javafx.scene.paint.Color;

/**
 * Created by yusiang on 10/4/14.
 */
public class Hydron extends Particle {
    public Hydron(double x, double y,double v) {
        super(x,y,1, Color.RED, v,1);
        //this.setFill(Color.DARKRED);
        type = "H+";

    }
}
