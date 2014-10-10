package particle;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * Created by Yusiang on 4/10/2014.
 */
public class Particle extends Circle {
    static Random r = new Random();
    Particle slave = null;

    String type;
    double theta; //Radians
    double v;
    double m;
    int sz;

    public Particle(double x, double y, int size, Paint paint, double vx) {
        super(x,y,size, paint);
        theta = r.nextDouble()*Math.PI;
        v = vx;
        sz = size;

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

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) { // 0<Theta<2PI
        while(theta<0) theta+=2*Math.PI;
        while(theta>2*Math.PI) theta-=2*Math.PI;
        this.theta = theta;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public int getSz() {
        return sz;
    }

    public void setSz(int sz) {
        this.sz = sz;
    }
}
