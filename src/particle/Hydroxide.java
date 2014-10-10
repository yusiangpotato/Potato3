package particle;

import javafx.scene.paint.Color;

/**
 * Created by yusiang on 10/4/14.
 */
public class Hydroxide extends Particle {
    public Hydroxide(double x,double y,double v) {
        super(x,y,3, Color.BLUE, v);
        //this.setFill(Color.BLUE);
        type = "OH-";
    }
}
