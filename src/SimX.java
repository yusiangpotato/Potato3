import javafx.application.Platform;

/**
 * Created by Yusiang on 4/10/2014.
 */
public class SimX extends Thread implements Runnable {
    SimulationLeft sl;
    SimulationRight sr;
    float collisionChance = 0.5f, explosionChance = 0.5f;

    public SimX(SimulationLeft sl, SimulationRight sr) {
        this.sl = sl;
        this.sr = sr;
////
    }

    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sl.step();
                sr.step();
            }
        });

    }
    
    public void setCollisionChance(float cc){
    	collisionChance = cc;
    }
    
    public float getCollisionChance(){
    	return collisionChance;
    }
    
    public void setExplosionChance(float ec){
    	explosionChance = ec;
    }
    
    public float getExplosionChance(){
    	return explosionChance;
    }
}
