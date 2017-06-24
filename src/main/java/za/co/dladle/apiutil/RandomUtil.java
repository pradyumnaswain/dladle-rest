package za.co.dladle.apiutil;

import java.util.Random;

/**
 * Created by prady on 1/8/2017.
 */
public class RandomUtil {
    public static Integer generateRandom() {
        Random r = new Random();
        int max = 9999;
        int min = 1000;
        return r.nextInt((max - min) + 1) + min;
    }
}
