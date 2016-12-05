package com.lewis.sort;

import java.util.Arrays;

/**
 * Created by zhangminghua on 2016/12/5.
 */
public class Test {
    public static void main(String[] args) {
        ISortable bubbleSott = new BubbleSort();
        ISortable insertSort = new InsertionSort();
        ISortable selectionSort = new SelectionSort();
        ISortable quickSort = new QuickSort();
        ISortable megerSort = new MergeSort();

        int[] array = ArrayFactory.createIntArray(1000, 1000000);
        int[] insertArray = Arrays.copyOf(array, array.length);
        int[] selectArray = Arrays.copyOf(array, array.length);
        int[] quickArray = Arrays.copyOf(array, array.length);
        int[] mergerArray = Arrays.copyOf(array, array.length);
        long beginTime = System.currentTimeMillis();
        bubbleSott.sort(array);
        System.out.println("bubbleSort costTime:"+(System.currentTimeMillis()-beginTime));

        beginTime = System.currentTimeMillis();
        insertSort.sort(insertArray);
        System.out.println("insertSort costTime:"+(System.currentTimeMillis()-beginTime));

        beginTime = System.currentTimeMillis();
        selectionSort.sort(selectArray);
        System.out.println("selectSort costTime:"+(System.currentTimeMillis()-beginTime));

        beginTime = System.currentTimeMillis();
        quickSort.sort(quickArray);
        System.out.println("quickSort costTime:"+(System.currentTimeMillis()-beginTime));

        beginTime = System.currentTimeMillis();
        megerSort.sort(mergerArray);
        System.out.println("mergerSort costTime:"+(System.currentTimeMillis()-beginTime));
    }
}
