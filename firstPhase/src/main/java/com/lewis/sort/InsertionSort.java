package com.lewis.sort;

import java.util.Arrays;

/**
 * Created by zhangminghua on 2016/12/3.
 * 插入排序：
 * 通常人们整理桥牌的方法是一张一张的来，将每一张牌插入到其他已经有序的牌中的适当位置。
 * 在计算机实现中，为了给要插入的元素腾出空间，我们需要将其余所有元素在插入之前都向右移动一位。
 * 与选择排序一样，当前索引左边的所有元素都是有序的，但是它们的最终位置还不确定，为了给更小的
 * 元素腾出空间，它们可能会被移动。但是当索引到达数组的右端时，数组排序就完成了。
 * 对于0到N-1之间的每个i,将a[i]与 a[0]到a[i-1]中比它小的所有元素依次有序的交换，在索引i由左向右
 * 的过程中，它左侧的元素总是有序的，所以当i到达数组的右端时排序就完成了。
 */
public class InsertionSort implements ISortable {

    public static void main(String[] args) {
        InsertionSort insertionSort = new InsertionSort();
        int[] array = ArrayFactory.createIntArray(10, 100);
        int[] newArray = Arrays.copyOf(array, array.length);
        int[] array1 = Arrays.copyOf(array, array.length);

        System.out.println("preSort:" + Arrays.toString(array));
        long beginTime = System.currentTimeMillis();
        insertionSort.advanceInsertSort(array);
        System.out.println("advanceInsertSort costTime is " + (System.currentTimeMillis() - beginTime));
        beginTime = System.currentTimeMillis();
        insertionSort.sort(newArray);
        System.out.println("sort costTime is " + (System.currentTimeMillis() - beginTime));
        insertionSort.insertSort(array1);
        System.out.println(Arrays.toString(array1));
    }

    public void sort(int[] array) {
        int length = array.length;
        for (int i = 1; i < length; i++) {
            //将array[i] 插入到array[i-1],array[i-2],array[i-3],array[i-4]...之中
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    SwapUtil.xorsSwap(array, j, j - 1);
                }
            }
        }
    }

    /**
     * 要大幅提高插入排序的速度，只需要在内循环中将较大的元素都向右移动，而不是总是交换两个元素，这样访问数组的次数就能减半
     */
    public void advanceInsertSort(int[] array) {
        int length = array.length;
        int j = 0;
        for (int i = 1; i < length; i++) {
            //待插入的元素
            int tmp = array[i];
            //找出待插入的元素tmp,要插入的索引位置j
            for (j = i; j > 0; j--) {
                //将待插入元素的位置之后的元素依次向右移动一位
                if (array[j - 1] > tmp) {
                    array[j] = array[j - 1];
                } else {
                    //到这，说明已经找到了待插入元素，要插入的位置j了
                    break;
                }
            }
            array[j] = tmp;
        }
    }

    //二分插入法排序
    public void binaryInsertSort(int[] array) {
        int length = array.length;
        int j = 0;
        for (int i = 1; i < length; i++) {
            int tmp = array[i];


        }
    }

    public void insertSort(int[] array) {
        int length = array.length;
        int j = 0;
        for (int i = 1; i < length; i++) {
            //element to be insert
            int tmp = array[i];
            for (j = i; j > 0; j--) {
                if (tmp > array[j-1]) {
                    break;
                } else {
                    array[j] = array[j - 1];
                }
            }
            array[j] = tmp;
        }

    }

}
