package particle;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * Created by Yusiang on 4/10/2014.
 */
public class Particle extends Circle{
    static Random r = new Random();
    Particle slave = null;

    String type;
    int theta;
    int v;
    int sz;
    public Particle(int size, Paint paint, int vx) {
        super(size, paint);
        theta=r.nextInt(360);
        v=vx;
        sz=size;

    }


    // Get/set

    public Particle getSlave() {
        return slave;
    }

    public void setSlave(Particle slave) {
        this.slave = slave;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTheta() {
        return theta;
    }

    public void setTheta(int theta) {
        this.theta = theta;
    }
}
