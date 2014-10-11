package particle;

import javafx.scene.paint.Color;

/**
 * Created by yusiang on 10/4/14.
 */
public class Hydroxide extends Particle {
    public Hydroxide(double x,double y,double v) {
        super(x,y,4, Color.BLUE, v,17);
        //this.setFill(Color.BLUE);
        type = "OH-";
    }
}
