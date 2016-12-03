package com.lewis.sort;

import java.util.Random;

/**
 * Created by zhangminghua on 2016/12/3.
 */
public class ArrayFactory {

    private static final Random random = new Random();

    public static int[]  createIntArray(int size,int max){
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(max);
        }
        return array;
    }
}
