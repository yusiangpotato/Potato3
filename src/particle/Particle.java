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
    boolean isSlaved = false;
    double storedEnergy = 0;
    String type;
    double theta; //Radians
    double v;
    double m;
    int sz;

    public Particle(double x, double y, int size, Paint paint, double vx, double ms) {
        super(x, y, size, paint);

        theta = r.nextDouble() * 2 * Math.PI;
        v = vx;
        sz = size;
        m = ms;

    }

    // Get/set

    public Particle getSlave() {
        return slave;
    }

    public void setSlave(Particle slave) {
        //Moving calculations here
        Particle p2 = this, p1 = slave;
        double p1x = p1.getCenterX(), p2x = p2.getCenterX(), p1y = p1.getCenterY(), p2y = p2.getCenterY();
        double p1v = p1.getV(), p2v = p2.getV(), p1t = p1.getTheta(), p2t = p2.getTheta();
        double p1m = p1.getM(), p2m = p2.getM();
        double dn = Math.sqrt(Math.pow(p1.getCenterX() - p2.getCenterX(), 2) + Math.pow(p1.getCenterY() - p2.getCenterY(), 2));
        p1.toFront();
        p2.toBack();
        double vf = (p1v * p1m + p2v * p2m) / (p1m + p2m);
        double es = p1m * sqr(p1v) / 2 + p2m * sqr(p2v) / 2 - (p1m + p2m) * sqr(vf) / 2;
        p2.setStoredEnergy(es < 0 ? 0 : es);//Energy?
        if (es < 0) System.out.println("!Es=" + es);
        p2.setV(vf); //Conservation of momentum
        p1.setCenterX(p2.getCenterX());
        p1.setCenterY(p2.getCenterY());
        p1.setV(0);


        this.slave = slave;
        slave.setSlaved(true);
    }

    public void rmSlave() {
        slave.setSlaved(false);
        slave = null;
    }

    public boolean hasSlave() {
        return slave != null;
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
        while (theta < 0) theta += 2 * Math.PI;
        while (theta > 2 * Math.PI) theta -= 2 * Math.PI;
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

    public double getM() {
        return hasSlave() ? m + slave.getM() : m;
    }

    public boolean isSlaved() {
        return isSlaved;
    }

    public void setSlaved(boolean isSlaved) {
        this.isSlaved = isSlaved;
    }

    public void setX(double n) {
        this.setCenterX(n);
        if (hasSlave()) {
            slave.setX(n);
        }
    }

    public void setY(double n) {
        this.setCenterY(n);
        if (hasSlave()) {
            slave.setY(n);
        }
    }

    public double getX() {
        return getCenterX();
    }

    public double getY() {
        return getCenterY();
    }

    public double getStoredEnergy() {
        return storedEnergy;
    }

    public void setStoredEnergy(double storedEnergy) {
        this.storedEnergy = storedEnergy;
    }

    double cos(double x) {
        return Math.cos(x);
    }

    double sin(double x) {
        return Math.sin(x);
    }

    double atan(double x) {
        return Math.atan(x);
    }

    double sqrt(double x) {
        return Math.sqrt(x);
    }

    double sqr(double x) {
        return Math.pow(x, 2);
    }
}
