import javafx.application.Platform;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Yusiang on 4/10/2014.
 */
public class SimX extends Thread implements Runnable {
    SimulationLeft sl;
    SimulationRight sr;
    ControlPanelHelper cph;
    ScheduledExecutorService SimXService;

    public SimX(ControlPanelHelper cph, SimulationLeft sl, SimulationRight sr) {
        this.sl = sl;
        this.sr = sr;
        this.cph = cph;
        SimXService = Executors.newSingleThreadScheduledExecutor();
        setExecFreq(50);
    }

    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sl.step();
                //sr.step();
                cph.updatepH();
            }
        });

    }

    public void setCollisionChance(double cc) {
        sl.setCollisionChance(cc);
    }

    public double getCollisionChance() {
        return sl.getCollisionChance();
    }

    public void setExplosionChance(double ec) {
        sl.setExplosionChance(ec);
    }

    public double getExplosionChance() {
        return sl.getExplosionChance();
    }

    public double getpH() {
        return sl.getpH();
    }


    public void addHplus() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sl.addParticle("H+");
            }
        });

    }

    public void addHA() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sl.addParticle("HA");
            }
        });

    }

    public void addAminus() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sl.addParticle("A-");
            }
        });

    }

    public void addOHminus() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sl.addParticle("OH-");
            }
        });

    }

    public void setExecFreq(double freq) throws IllegalArgumentException {
        if (freq > 10000 || freq < 0) throw new IllegalArgumentException("And what do you think you are doing, hmm?");

        shutdown();
        SimXService = Executors.newSingleThreadScheduledExecutor();
        if (freq == 0) return;
        SimXService.scheduleWithFixedDelay(this, 0, Math.round(1E6 / freq), TimeUnit.MICROSECONDS);

    }

    public void shutdown() {
        SimXService.shutdown();
    }

    public void clear() {
        sl.clear();
    }

    public boolean execCmd(String cmd) {
        cmd = cmd.toUpperCase();
        String[] x = cmd.split(" ");
        try {
            switch (x[0]) {
                case "FREQ":
                    setExecFreq(Double.parseDouble(x[1]));
                    return true;
                case "H+":
                case "H":
                    for (int i = 0; i < Integer.parseInt(x[1]); i++)
                        addHplus();
                    return true;
                case "HA":
                    for (int i = 0; i < Integer.parseInt(x[1]); i++)
                        addHA();
                    return true;
                case "A":
                case "A-":
                    for (int i = 0; i < Integer.parseInt(x[1]); i++)
                        addAminus();
                    return true;
                case "OH":
                case "OH-":
                    for (int i = 0; i < Integer.parseInt(x[1]); i++)
                        addOHminus();
                    return true;
                case "X":
                case "CLR":
                    clear();
                    return true;
                case "STEP":
                    if (x.length == 1) {
                        run();
                        return true;
                    }
                case "FF":
                    for (int i = 0; i < Integer.parseInt(x[1]); i++)
                        run();
                    return true;
                case "CP":
                    double cp = Double.parseDouble(x[1]);
                    if (cp < 0 || cp > 1) return false;
                    setCollisionChance(cp);
                    cph.updateSliders();
                    return true;
                case "EP":
                    double ep = Double.parseDouble(x[1]);
                    if (ep < 0 || ep > 1) return false;
                    setExplosionChance(ep);
                    cph.updateSliders();
                    return true;
                case "WHEE":
                    sl.whee();
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
        //return true;
    }

}
