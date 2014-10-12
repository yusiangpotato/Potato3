import java.util.Random;

/**
 * Created by yusiang on 10/11/14.
 */
public class Rng {
    static Random r = new Random();

    static boolean chance(double d) {
        return d > r.nextDouble();
    }
}
