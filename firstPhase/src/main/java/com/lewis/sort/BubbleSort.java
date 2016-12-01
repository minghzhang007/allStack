package com.lewis.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/12/1.
 */
public class BubbleSort {

    public static void main(String[] args) {
        byte[] array = createArray(100000);
        byte[] newArray = Arrays.copyOf(array, array.length);
        System.out.println(Arrays.toString(array));
        long beginTime = System.currentTimeMillis();
        byte[] sortedArray = sortOfXORsSwap(array);
        System.out.println("costTime is "+(System.currentTimeMillis()-beginTime));
        beginTime = System.currentTimeMillis();
        sortOfSwap(newArray);
        System.out.println("costTime is "+(System.currentTimeMillis()-beginTime));
    }


    public static byte[] createArray(int size) {
        Random r = new Random();
        byte[] array = new byte[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) r.nextInt(128);
        }
        return array;
    }

    public static byte[] sortOfSwap(byte[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (array[i] < array[j]) {
                    swap(array,i,j);
                }
            }
        }
        return array;
    }

    public static byte[] sortOfXORsSwap(byte[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (array[i] < array[j]) {
                    XORsSwap(array,i,j);
                }
            }
        }
        return array;
    }

    /**
     * 使用异或运算^进行数据交换
     */
    private static void XORsSwap(byte[] array, int i, int j) {
        array[i] = (byte) (array[i] ^ array[j]);
        array[j] = (byte) (array[j] ^ array[i]);
        array[i] = (byte) (array[i] ^ array[j]);
    }


    private static void swap(byte[] array, int i, int j) {
        byte tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

}
