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
    int every = 1;
    String execs = "";
    final int cnt[] = {1};

    public SimX(ControlPanelHelper cph, SimulationLeft sl, SimulationRight sr) {
        this.sl = sl;
        this.sr = sr;
        this.cph = cph;
        SimXService = Executors.newSingleThreadScheduledExecutor();
        setExecFreq(50);
        //for(int i=0;i<1000;i++) run();
    }

    @Override
    public void run() {
        ;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sl.step();
                //sr.step();
                cph.updatepH();
                if (every != 0 && cnt[0] % every == 0)
                    execCmd(execs);
            }
        });
        cnt[0]++;
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
        try {
            cmd = cmd.toUpperCase();
            String[] x = cmd.split(" ");

            for (int i = 0; i < x.length; i++) {
                if (!x[0].equals("EVERY") && x[i].equals("AND")) {
                    String nextExec = "";
                    for (int j = i + 1; j < x.length; j++) {
                        nextExec += x[j] + " ";
                    }
                    execCmd(nextExec);
                    break;
                }

            }
            for (int i = 0; i < x.length; i++) {
                if (!x[0].equals("EVERY") && x[i].equals("@"))
                    x[i] = "" + sl.getStepN();
            }
            switch (x[0]) {
                case "FREQ":
                    setExecFreq(Double.parseDouble(x[1]));
                    return true;
                case "H+":
                case "H":
                    if (x.length == 1) {
                        addHplus();
                        return true;
                    }
                    for (int i = 0; i < Integer.parseInt(x[1]); i++)
                        addHplus();
                    return true;
                case "HA":
                    if (x.length == 1) {
                        addHA();
                        return true;
                    }
                    for (int i = 0; i < Integer.parseInt(x[1]); i++)
                        addHA();
                    return true;
                case "A":
                case "A-":
                    if (x.length == 1) {
                        addAminus();
                        return true;
                    }
                    for (int i = 0; i < Integer.parseInt(x[1]); i++)
                        addAminus();
                    return true;
                case "OH":
                case "OH-":
                    if (x.length == 1) {
                        addOHminus();
                        return true;
                    }
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
                    return true;
                case "PUN":
                    setCollisionChance(.4f);
                    setExplosionChance(0f);
                    cph.updateSliders();
                    sl.setCollCull(false);
                    sl.setEdgeCull(false);
                    sl.setCOLL_ENABLED(false);
                    return true;
                case "RESET":
                case "DEFAULT":
                case "DEPUN":
                case "DEAHN":
                    setCollisionChance(.5f);
                    setExplosionChance(.5f);
                    cph.updateSliders();
                    sl.setCollCull(true);
                    sl.setEdgeCull(true);
                    sl.setCOLL_ENABLED(true);
                    clear();
                    execCmd("a 50 and h 50 and ha 50");
                    execs = "";
                    every = 0;
                    sl.setTransparent(false);
                    setExecFreq(50);
                    return true;
                case "CC":
                    sl.setCollCull(Integer.parseInt(x[1]) % 2 != 0);
                    return true;
                case "EC":
                    sl.setEdgeCull(Integer.parseInt(x[1]) % 2 != 0);
                    return true;
                case "CE":
                    sl.setCOLL_ENABLED(Integer.parseInt(x[1]) % 2 != 0);
                    return true;
                case "PINK":
                case "FLUFFY":
                case "UNICORNS":
                case "RAINBOWS":
                    sl.rainbow();
                    return true;
                case "DISCO":
                    execCmd("freq 100 and every 10 rainbows");
                    return true;

                /*
                case "XSZ":
                    sl.setXsz(Integer.parseInt(x[1]));
                    return true;
                case "YSZ":
                    sl.setYsz(Integer.parseInt(x[1]));
                    return true;
                */
                case "TRANS":
                case "ALPHA":
                    sl.setTransparent(Integer.parseInt(x[1]) % 2 == 0);
                    return true;
                case "AHN":
                    execCmd("freq 20 and every 1 alpha @");
                    return true;


                case "EVERY":

                    if (Integer.parseInt(x[1]) == 0) {
                        execs = "";
                        return true;
                    } else if (every != Integer.parseInt(x[1])) {
                        execs = "";
                    }
                    every = Integer.parseInt(x[1]);
                    //execs="";
                    execs += "and ";
                    for (int i = 2; i < x.length; i++) {
                        execs += x[i] + " ";
                    }
                    return true;
                case "POTATE":
                    execCmd("x and a 250 and disco");
                    return true;
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
        //return true;.
    }

}
