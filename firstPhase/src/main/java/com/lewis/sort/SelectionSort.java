package com.lewis.sort;

import java.util.Arrays;

/**
 * Created by zhangminghua on 2016/12/3.
 * 选择排序：（为什么叫选择排序：因为它不断的选择剩余的元素之中最小的元素）
 * 首先找出数组中最小的元素，将它和数组的第一个元素交换位置；
 * 再次，在剩下的元素中找出最小的元素，将它与数组的第二个元素交换位置；
 * 如此往复，直到整个数组排序完成。
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] array = ArrayFactory.createIntArray(200, 1000);
        System.out.println("preSort :" + Arrays.toString(array));
        sort(array);
        System.out.println("postSort:" + Arrays.toString(array));
    }

    public static void sort(int[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if (array[min] > array[j]) {
                    min = j;
                }
            }
            SwapUtil.xorsSwap(array, min, i);
        }
    }


}
