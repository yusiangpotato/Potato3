package particle;

import javafx.scene.paint.Color;

/**
 * Created by yusiang on 10/4/14.
 */
public class Anion extends Particle {
    public Anion(int v){
        super(30, Color.GREENYELLOW,v);
        //this.setFill(Color.Gr);
        this.type="A-";
    }
}
