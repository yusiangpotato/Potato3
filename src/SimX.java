import java.util.ArrayList;

/**
 * Created by Yusiang on 4/10/2014.
 */
public class SimX extends Thread implements Runnable{
        SimulationLeft sl;
        SimulationRight sr;
    public SimX (SimulationLeft sl, SimulationRight sr){
        this.sl=sl;
        this.sr=sr;
////
    }
    @Override
    public void run(){

    }
}
