
public class Exma {
    //Exponential moving average class
    private double alpha = Double.NaN, value = Double.NaN;
    private boolean hasInit = false;

    public Exma(double a) {
        if (a > 1 || a < 0) return;
        alpha = a;
    }

    public void init(double val) {
        hasInit = true;
        value = val;
    }

    public void update(double val) {
        value = hasInit ? alpha * val + (1 - alpha) * value : val;
        hasInit = true;
    }

    public double getValue() {
        return value;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public boolean isInit() {
        return hasInit;
    }
}
