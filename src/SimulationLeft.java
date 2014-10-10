import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import particle.*;

import java.util.ArrayList;
import java.util.Random;

public class SimulationLeft {
    static final int Xsz=500, Ysz=650;
    static final boolean ENH_CULL=false; //Make sure no particles "stick" to wall sides at the cost of precision
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
        for(int i=0;i<100;i++){
            particleList.add(new Hydroxide(r.nextDouble()*Xsz,r.nextDouble()*Ysz,r.nextGaussian()));

        }
        for (Particle px: particleList)
            p.getChildren().add(px);
        return p;
    }

    public void step() {
        stepn++;
        Lstepn.setText(stepn + "");
        for(Particle px: particleList){ //Step 1, move
            px.setCenterX(px.getCenterX()+px.getV()*Math.cos(px.getTheta()));
            px.setCenterY(px.getCenterY() + px.getV() * Math.sin(px.getTheta()));
        }
        for(Particle px: particleList){ //Step 2, check wall collision
            if(px.getCenterX()+px.getSz()>Xsz) { //Negate and add PI
                px.setTheta(Math.PI-px.getTheta());
                if(ENH_CULL) px.setCenterX(Xsz-px.getSz()-1);
            }
            if(px.getCenterX()-px.getSz()<0) { //Negate and add PI
                px.setTheta(Math.PI-px.getTheta());
                if(ENH_CULL) px.setCenterX(px.getSz()+1);
            }
            if(px.getCenterY()+px.getSz()>Ysz) { //Negate
                px.setTheta(-px.getTheta());
                if(ENH_CULL) px.setCenterY(Ysz-px.getSz()-1);
            }
            if(px.getCenterY()-px.getSz()<0) { //Negate
                px.setTheta(-px.getTheta());
                if(ENH_CULL) px.setCenterY(px.getSz()+1);
            }
        }
        for(int t1=0;t1<particleList.size();t1++){//Step 3, particle-particle collisions
            for(int t2=t1+1;t2<particleList.size();t2++){
                Particle p1=particleList.get(t1), p2=particleList.get(t2);
                //Get distance from p1 to p2 using pythags
                double p1x=p1.getCenterX(),p2x=p2.getCenterX(),p1y=p1.getCenterY(),p2y=p2.getCenterY();
                double dn=Math.sqrt(Math.pow(p1.getCenterX()-p2.getCenterX(),2)+Math.pow(p1.getCenterY()-p2.getCenterY(),2));
                if(dn < p1.getSz() + p2.getSz()){//Here we go again
                    double phi=Math.atan((p1.getCenterY()-p2.getCenterY())/(p1.getCenterX()-p2.getCenterX()));
                    if(p1.getCenterX()-p2.getCenterX()<0) phi+=Math.PI;
                    if(phi<2*Math.PI) phi +=2*Math.PI;
                    if(false){//Case 1: p1 no slave, 2 particles combine

                    }else if(false){//Case 2: p1 has slave and successful collision -> p1 explodes

                    }else{//No success, see https://en.wikipedia.org/wiki/Elastic_collision#Two-Dimensional_Collision_With_Two_Moving_Objects
                          //http://williamecraver.wix.com/elastic-equations

                        p1.setV(0);
                        p2.setV(0);
                        //Cull loopthrough for p2
                        /* Algo faulty do not use
                        p2.setCenterX(p1x+(p1.getSz()+p2.getSz())*Math.cos(phi));
                        p2.setCenterY(p1x+(p1.getSz()+p2.getSz())*Math.sin(phi));
                        */
                    }
                }

            }
        }

    }

}
