package particle;

import javafx.scene.paint.Color;

/**
 * Created by yusiang on 10/4/14.
 */
public class Anion extends Particle { //The usage is HCO2-
    public Anion(double x,double y,double v) {
        super(x,y,5, Color.GREENYELLOW, v);
        //this.setFill(Color.Gr);
        this.type = "A-";
    }
}
