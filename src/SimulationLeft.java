import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import particle.*;

import java.util.ArrayList;
import java.util.Random;

public class SimulationLeft {
    static final int Xsz = 500, Ysz = 650;
    static final boolean ENH_EDGE_CULL = true; //Make sure no particles "stick" to wall sides at the cost of precision
    static final boolean ENH_COLL_CULL = true; //Make sure no particles "stick" to each other at the cost of precision
    ArrayList<Particle> particleList = new ArrayList<Particle>();
    Pane p;
    int stepn = 0;
    Label Lstepn = new Label(stepn + "");


    public Pane createSimulation() {
        Random r = new Random();
        p = new Pane();
        p.setMinSize(Xsz, Ysz);
        p.setPrefSize(Xsz, Ysz);
        p.setMaxSize(Xsz, Ysz);
        p.getChildren().addAll(Lstepn);
        for (int i = 0; i < 101; i++) {
            particleList.add(new Anion(r.nextDouble() * Xsz, r.nextDouble() * Ysz, r.nextGaussian()));
            particleList.add(new Hydron(r.nextDouble() * Xsz, r.nextDouble() * Ysz, r.nextGaussian()));
            particleList.add(new Hydroxide(r.nextDouble() * Xsz, r.nextDouble() * Ysz, r.nextGaussian()));

        }
        for (Particle px : particleList)
            p.getChildren().add(px);
        return p;
    }

    public void step(){
        stepn++;
        Lstepn.setText(stepn + "");

        for (Particle px : particleList) { //Step 1, move
            px.setCenterX(px.getCenterX() + px.getV() * Math.cos(px.getTheta()));
            px.setCenterY(px.getCenterY() + px.getV() * Math.sin(px.getTheta()));
        }
        for (Particle px : particleList) { //Step 2, check wall collision
            if (px.getCenterX() + px.getSz() > Xsz) { //Negate and add PI
                px.setTheta(Math.PI - px.getTheta());
                if (ENH_EDGE_CULL) px.setCenterX(Xsz - px.getSz() - 1);
            }
            if (px.getCenterX() - px.getSz() < 0) { //Negate and add PI
                px.setTheta(Math.PI - px.getTheta());
                if (ENH_EDGE_CULL) px.setCenterX(px.getSz() + 1);
            }
            if (px.getCenterY() + px.getSz() > Ysz) { //Negate
                px.setTheta(-px.getTheta());
                if (ENH_EDGE_CULL) px.setCenterY(Ysz - px.getSz() - 1);
            }
            if (px.getCenterY() - px.getSz() < 0) { //Negate
                px.setTheta(-px.getTheta());
                if (ENH_EDGE_CULL) px.setCenterY(px.getSz() + 1);
            }
        }
        //Step 3, particle-particle collisions
        for (int t1 = 0; t1 < particleList.size(); t1++) {
            for (int t2 = t1+1; t2 < particleList.size(); t2++) {
                //if (t1 == t2) continue;
                Particle p1 = particleList.get(t1), p2 = particleList.get(t2);
                if(p1.isSlaved()||p2.isSlaved()) continue; //Don't touch plz

                //Convenience thingies
                double p1x = p1.getCenterX(), p2x = p2.getCenterX(), p1y = p1.getCenterY(), p2y = p2.getCenterY();
                double p1v = p1.getV(), p2v = p2.getV(), p1t = p1.getTheta(), p2t = p2.getTheta();
                double p1m = p1.getM(), p2m = p2.getM();
                double dn = Math.sqrt(Math.pow(p1.getCenterX() - p2.getCenterX(), 2) + Math.pow(p1.getCenterY() - p2.getCenterY(), 2));
                if (dn < p1.getSz() + p2.getSz()) {//Here we go again
                    double phi = Math.atan((p2y - p1y) / (p2x - p1x));
                    if (p2.getCenterX() - p1.getCenterX() < 0) phi += Math.PI;
                    //if (phi < 2 * Math.PI) phi += 2 * Math.PI;
                    boolean combine = false, p1explode = false, p2explode = false, nothing = false;
                    //TODO Determine conditions here
                    nothing = true;

                    //Work on conditions
                    if (combine) {//p1,p2 no slave, 2 particles combine
                        if(p1.getSz()<p2.getSz()){ //The slave MUST be smaller than the master.
                            p2.setSlave(p1);
                        }else{
                            p1.setSlave(p2);
                        }
                    }
                    if (p1explode) {//p1 has slave and successful collision -> p1 explodes

                    }
                    if (p2explode) {//Ditto

                    }
                    if (nothing) {//No success, see https://en.wikipedia.org/wiki/Elastic_collision#Two-Dimensional_Collision_With_Two_Moving_Objects
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
                            p2.setCenterX(p1x + (p1.getSz() + p2.getSz()) * Math.cos(phi));
                            p2.setCenterY(p1y + (p1.getSz() + p2.getSz()) * Math.sin(phi));
                        }

                    }
                }

            }
        }

    }

    double cos(double x) {
        return Math.cos(x);
    }

    double sin(double x) {
        return Math.sin(x);
    }

    double sqrt(double x) {
        return Math.sqrt(x);
    }

    double sqr(double x) {
        return Math.pow(x, 2);
    }

}
