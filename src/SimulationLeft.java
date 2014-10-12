import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import particle.Anion;
import particle.Hydron;
import particle.Hydroxide;
import particle.Particle;

import java.util.ArrayList;
import java.util.Random;

public class SimulationLeft {
    static final int Xsz = 800, Ysz = 650;
    static double CF = 2, eki;
    static final boolean ENH_EDGE_CULL = true; //Make sure no particles "stick" to wall sides at the cost of precision
    static final boolean ENH_COLL_CULL = true; //Make sure no particles "stick" to each other at the cost of precision
    ArrayList<Particle> particleList = new ArrayList<Particle>();
    Pane p;
    int stepn = 0;
    double Hplus = Double.NaN;
    Exma HplusExma = new Exma(0.01);
    double collisionChance = 0.5f, explosionChance = 0.5f;
    Label Lstepn = new Label(stepn + "");
    Random r = new Random();

    public Pane createSimulation() {
        Label init = new Label("Initializing... ");

        p = new Pane();
        p.setMinSize(Xsz, Ysz);
        p.setPrefSize(Xsz, Ysz);
        p.setMaxSize(Xsz, Ysz);
        //p.getChildren().addAll(Lstepn);
        //p.getChildren().add(init);
        for (int i = 0; i < 50; i++) {
            particleList.add(new Anion(r.nextDouble() * Xsz, r.nextDouble() * Ysz, r.nextGaussian()));
            particleList.add(new Hydron(r.nextDouble() * Xsz, r.nextDouble() * Ysz, r.nextGaussian()));
            //particleList.add(new Hydroxide(r.nextDouble() * Xsz, r.nextDouble() * Ysz, r.nextGaussian()));

        }
        for (Particle px : particleList) {
            p.getChildren().add(px);
            eki += px.getM() * sqr(px.getV()) / 2;
        }
        return p;
    }

    public void step() {
        stepn++;
        //Lstepn.setText(stepn + "");
        double ek = 0;
        Hplus = 1E-7;
        for (Particle px : particleList) { //Step 1, move
            if (px.isSlaved()) continue; //Don't touch plz
            px.setX(px.getCenterX() + px.getV() * Math.cos(px.getTheta()));
            px.setY(px.getCenterY() + px.getV() * Math.sin(px.getTheta()));
            ek += px.getM() * sqr(px.getV()) / 2;
            if (px.getType().equals("H+")) Hplus += 1E-6;
            //if(px.getType().equals("OH-"))Hplus-=1E-6;
        }
        HplusExma.update(Hplus);

        if (ek > eki) CF *= 0.99;
        if (ek < eki) CF *= 1.01;
        if (CF > 3) CF = 3;
        if (CF < 1) CF = 1;
        //Lstepn.setText(stepn + "\n" + ek + "\n" + CF);
        Lstepn.setText(Lstepn.getText().equals("-") ? "\\" : Lstepn.getText().equals("\\") ? "|" : "-");
        for (Particle px : particleList) { //Step 2, check wall collision
            if (px.isSlaved()) continue; //Don't touch plz
            if (px.getCenterX() + px.getSz() > Xsz) { //Negate and add PI
                px.setTheta(Math.PI - px.getTheta());
                if (ENH_EDGE_CULL) px.setX(Xsz - px.getSz() - 1);
            }
            if (px.getCenterX() - px.getSz() < 0) { //Negate and add PI
                px.setTheta(Math.PI - px.getTheta());
                if (ENH_EDGE_CULL) px.setX(px.getSz() + 1);
            }
            if (px.getCenterY() + px.getSz() > Ysz) { //Negate
                px.setTheta(-px.getTheta());
                if (ENH_EDGE_CULL) px.setY(Ysz - px.getSz() - 1);
            }
            if (px.getCenterY() - px.getSz() < 0) { //Negate
                px.setTheta(-px.getTheta());
                if (ENH_EDGE_CULL) px.setY(px.getSz() + 1);
            }
        }
        //Step 3, particle-particle collisions
        for (int t1 = 0; t1 < particleList.size(); t1++) {
            for (int t2 = t1 + 1; t2 < particleList.size(); t2++) {
                //if (t1 == t2) continue;
                Particle p1 = particleList.get(t1), p2 = particleList.get(t2);
                if (p1.isSlaved() || p2.isSlaved()) continue; //Don't touch plz

                //Convenience thingies
                double p1x = p1.getCenterX(), p2x = p2.getCenterX(), p1y = p1.getCenterY(), p2y = p2.getCenterY();
                double p1v = p1.getV(), p2v = p2.getV(), p1t = p1.getTheta(), p2t = p2.getTheta();
                double p1m = p1.getM(), p2m = p2.getM();
                double dn = Math.sqrt(Math.pow(p1.getCenterX() - p2.getCenterX(), 2) + Math.pow(p1.getCenterY() - p2.getCenterY(), 2));
                if (dn < p1.getSz() + p2.getSz()) {//Here we go again
                    double phi = Math.atan((p2y - p1y) / (p2x - p1x));
                    if (p2.getCenterX() - p1.getCenterX() < 0) phi += Math.PI;
                    //if (phi < 2 * Math.PI) phi += 2 * Math.PI;
                    boolean combine = false, p1explode = false, p2explode = false, neutralize = false;

                    if (!p1.hasSlave() && !p2.hasSlave())
                        if (Rng.chance(collisionChance)) {
                            if (p1.getType().equals("A-") && p2.getType().equals("H+")) {
                                combine = true;
                            }
                            if (p2.getType().equals("A-") && p1.getType().equals("H+")) {
                                combine = true;
                            }
                        }
                    if (Rng.chance(1f)) {
                        if (p1.getType().equals("OH-") && p2.getType().equals("H+")) {
                            neutralize = true;
                        }
                        if (p2.getType().equals("OH-") && p1.getType().equals("H+")) {
                            neutralize = true;
                        }
                    }

                    if (p1.hasSlave()) {
                        if (Rng.chance(explosionChance)) //ditto
                            p1explode = true;
                    }
                    if (p2.hasSlave()) {
                        if (Rng.chance(explosionChance)) //ditto
                            p2explode = true;
                    }


                    //Work on conditions
                    if (combine) {//p1,p2 no slave, 2 particles combine
                        if (p1.getSz() < p2.getSz()) { //The slave MUST be smaller than the master.
                            p2.setSlave(p1);

                        } else {
                            p1.setSlave(p2);
                        }

                    } else if (neutralize) {
                        p1.setVisible(false);
                        p2.setVisible(false);
                        p.getChildren().remove(p1);
                        p.getChildren().remove(p2);
                        eki -= p1.getM() * sqr(p1.getV()) / 2;
                        eki -= p2.getM() * sqr(p2.getV()) / 2;
                        //REMEMBER TO CLEANUP!
                    } else {//No combine: Bounce off
                        //http://williamecraver.wix.com/elastic-equations
                        //Argh math math math yuck
                        double p1vxf = ((p1v * cos(p1t - phi) * (p1m - p2m) + 2 * p2m * p2v * cos(p2t - phi)) / (p1m + p2m)) * cos(phi) + p1v * sin(p1t - phi) * cos(phi + Math.PI / 2);
                        double p1vyf = ((p1v * cos(p1t - phi) * (p1m - p2m) + 2 * p2m * p2v * cos(p2t - phi)) / (p1m + p2m)) * sin(phi) + p1v * sin(p1t - phi) * sin(phi + Math.PI / 2);
                        double p2vxf = ((p2v * cos(p2t - phi) * (p2m - p1m) + 2 * p1m * p1v * cos(p1t - phi)) / (p1m + p2m)) * cos(phi) + p2v * sin(p2t - phi) * cos(phi + Math.PI / 2);
                        double p2vyf = ((p2v * cos(p2t - phi) * (p2m - p1m) + 2 * p1m * p1v * cos(p1t - phi)) / (p1m + p2m)) * sin(phi) + p2v * sin(p2t - phi) * sin(phi + Math.PI / 2);

                        double p1vf = sqrt(sqr(p1vxf) + sqr(p1vyf));
                        double p1tf = Math.atan(p1vyf / p1vxf);
                        if (p1vxf < 0) p1tf += Math.PI;

                        double p2vf = sqrt(sqr(p2vxf) + sqr(p2vyf));
                        double p2tf = Math.atan(p2vyf / p2vxf);
                        if (p2vxf < 0) p2tf += Math.PI;

                        p1.setV(p1vf);
                        p1.setTheta(p1tf);
                        p2.setV(p2vf);
                        p2.setTheta(p2tf);
                        if (ENH_COLL_CULL) {//Cull loopthrough for p2
                            p2.setX(p1x + (p1.getSz() + p2.getSz()) * Math.cos(phi));
                            p2.setY(p1y + (p1.getSz() + p2.getSz()) * Math.sin(phi));
                        }

                    }
                    if (p1explode) {//p1 has slave and successful collision -> p1 explodes and vice versa
                        Particle p3 = p1.getSlave();
                        p1.rmSlave();
                        double es = CF * p1.getStoredEnergy();
                        double vxi = p1.getV() * cos(p1.getTheta()), vyi = p1.getV() * sin(p1.getTheta());
                        double p1vx = 0, p1vy = 0, p3vx = 0, p3vy = 0, lambda = 2 * Math.PI * r.nextDouble(), tmp1v = sqrt((2 * es) / (sqr(p1m) + p1m)), tmp3v = sqrt((2 * es) / (sqr(p3.getM()) + p3.getM()));
                        p1vx = vxi + tmp1v * cos(lambda);
                        p1vy = vyi + tmp1v * sin(lambda);
                        p3vx = vxi + tmp3v * cos(Math.PI + lambda);
                        p3vy = vyi + tmp3v * sin(Math.PI + lambda);
                        p1.setV(sqrt(sqr(p1vx) + sqr(p1vy)));
                        p1.setTheta((p1vx < 0 ? Math.PI : 0) + atan(p1vy / p1vx));
                        p3.setV(sqrt(sqr(p3vx) + sqr(p3vy)));
                        p3.setTheta((p3vx < 0 ? Math.PI : 0) + atan(p3vy / p3vx));
                        p1.setStoredEnergy(0);
                        p3.setX(p1x + (p1.getSz() + p3.getSz()) * Math.cos(lambda));
                        p3.setY(p1y + (p1.getSz() + p3.getSz()) * Math.sin(lambda));
                    }

                    if (p2explode) {
                        Particle p3 = p2.getSlave();
                        p2.rmSlave();
                        double es = CF * p2.getStoredEnergy();
                        double vxi = p2.getV() * cos(p2.getTheta()), vyi = p2.getV() * sin(p2.getTheta());
                        double p2vx = 0, p2vy = 0, p3vx = 0, p3vy = 0, lambda = 2 * Math.PI * r.nextDouble(), tmp2v = sqrt((2 * es) / (sqr(p2m) + p2m)), tmp3v = sqrt((2 * es) / (sqr(p3.getM()) + p3.getM()));
                        p2vx = vxi + tmp2v * cos(lambda);
                        p2vy = vyi + tmp2v * sin(lambda);
                        p3vx = vxi + tmp3v * cos(Math.PI + lambda);
                        p3vy = vyi + tmp3v * sin(Math.PI + lambda);
                        p2.setV(sqrt(sqr(p2vx) + sqr(p2vy)));
                        p2.setTheta((p2vx < 0 ? Math.PI : 0) + atan(p2vy / p2vx));
                        p3.setV(sqrt(sqr(p3vx) + sqr(p3vy)));
                        p3.setTheta((p3vx < 0 ? Math.PI : 0) + atan(p3vy / p3vx));
                        p2.setStoredEnergy(0);
                        p3.setX(p1x + (p2.getSz() + p3.getSz()) * Math.cos(lambda));
                        p3.setY(p1y + (p2.getSz() + p3.getSz()) * Math.sin(lambda));
                    }


                }

            }
        }
        for (int i = 0; i < particleList.size(); ) {
            Particle px = particleList.get(i);
            if (!px.isVisible()) particleList.remove(i);
            else i++;
        }

    }

    public void addParticle(String type) {
        if (particleList.size() > 10000) {
            //Sorry
            System.out.println("10k particles reached");
            return;
        }

        Particle px;
        switch (type) {
            case "OH-":
                px = new Hydroxide(r.nextDouble() * Xsz, r.nextDouble() * Ysz, r.nextGaussian());
                break;
            case "H+":
                px = new Hydron(r.nextDouble() * Xsz, r.nextDouble() * Ysz, r.nextGaussian());
                break;
            case "A-":
                px = new Anion(r.nextDouble() * Xsz, r.nextDouble() * Ysz, r.nextGaussian());
                break;
            case "HA":
                px = new Anion(r.nextDouble() * Xsz, r.nextDouble() * Ysz, r.nextGaussian());
                px.setSlave(new Hydron(r.nextDouble() * Xsz, r.nextDouble() * Ysz, r.nextGaussian()));
                break;
            default:
                System.out.println("Unknown type: " + type);
                return;
        }
        eki += px.getM() * sqr(px.getV()) / 2;
        p.getChildren().add(px);
        particleList.add(px);
        if (px.hasSlave()) {
            p.getChildren().add(px.getSlave());
            particleList.add(px.getSlave());
        }

    }

    double getpH() {
        return -Math.log10(HplusExma.getValue());
    }

    public double getCollisionChance() {
        return collisionChance;
    }

    public void setCollisionChance(double collisionChance) {
        this.collisionChance = collisionChance;
    }

    public double getExplosionChance() {
        return explosionChance;
    }

    public void setExplosionChance(double explosionChance) {
        this.explosionChance = explosionChance;
    }

    public void clear() {
        p.getChildren().clear();
        particleList.clear();
        eki = 0;
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

    void whee() {
        for (Particle px : particleList)
            px.setTheta(Math.PI + px.getTheta());
    }


}
