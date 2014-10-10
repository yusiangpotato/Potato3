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
        for(Particle px: particleList){ //It 1, move
            px.setCenterX(px.getCenterX()+px.getV()*Math.cos(px.getTheta()));
            px.setCenterY(px.getCenterY() + px.getV() * Math.sin(px.getTheta()));
        }
        for(Particle px: particleList){ //It 2, check wall collision
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

    }

}
