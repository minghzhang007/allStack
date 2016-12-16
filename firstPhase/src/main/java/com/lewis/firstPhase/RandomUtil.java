package com.lewis.firstPhase;

import java.util.Random;

/**
 * Created by zhangminghua on 2016/11/20.
 */
public final class RandomUtil {
    private RandomUtil(){}
    public static int getRandomInt(int min,int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);// [0,62)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
