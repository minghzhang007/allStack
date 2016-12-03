package com.lewis.sort;

import java.util.Arrays;

/**
 * Created by zhangminghua on 2016/12/3.
 */
public class BubbleSort implements ISortable{
    public static void main(String[] args) {
        int[] intArray = ArrayFactory.createIntArray(20, 1000);
        System.out.println(Arrays.toString(intArray));
    }

    public void sort(int[] array){
        for (int i = 0; i < array.length; i++) {
            for (int j = i+1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    SwapUtil.xorsSwap(array,i,j);
                }
            }
        }
    }
}
