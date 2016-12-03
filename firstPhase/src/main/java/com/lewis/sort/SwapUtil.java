package com.lewis.sort;

/**
 * Created by zhangminghua on 2016/12/3.
 */
public class SwapUtil {

    /**
     * 异或运算^ 满足：
     * 1.交换律 a ^ b = b ^ a
     * 2.结合律 (a ^ b)^c= a ^ (b ^ c)
     * 3.对于任何数都有 a ^ a = 0;a ^ 0 = a;
     * 4.自反性 a ^ b ^ b = a ^ 0 = a
     */
    public static void xorsSwap(int[] array, int i, int j) {
        if (i != j) {
            array[i] = array[i] ^ array[j];
            array[j] = array[j] ^ array[i];
            array[i] = array[i] ^ array[j];
        }
    }
}
