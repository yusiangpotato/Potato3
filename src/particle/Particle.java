package particle;

import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * Created by Yusiang on 4/10/2014.
 */
public class Particle extends Circle{
    static Random r = new Random();
    String type;
    int theta=r.nextInt(360);


}
