package com.lewis.sort;

import java.util.Arrays;

/**
 * Created by zhangminghua on 2016/12/3.
 * 快速排序：
 * 1． 先从数列中取出一个数作为基准数。
 * 2． 分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
 * 3． 再对左右区间重复第二步，直到各区间只有一个数。
 * 对挖坑填数进行总结
 * 1． i =L; j = R; 将基准数挖出形成第一个坑 a[i]。
 * 2． j--由后向前找比它小的数，找到后挖出此数填前一个坑 a[i]中。
 * 3． i++由前向后找比它大的数，找到后也挖出此数填到前一个坑 a[j]中。
 * 4． 再重复执行 2， 3 二步，直到 i==j，将基准数填入 a[i]中。
 */
public class QuickSort implements ISortable{

    public static void main(String[] args) {
        int[] array = ArrayFactory.createIntArray(10, 100);
        System.out.println(Arrays.toString(array));
        QuickSort quickSort = new QuickSort();
        quickSort.sort(array);
        System.out.println(Arrays.toString(array));
    }



    //返回调整后基准数的位置
    public static int adjustArray(int[] array, int left, int right) {
        //array[left] 就是第一个坑
        int vo = array[left];
        int i = left;
        int j = right;
        while (i < j) {
            //从右向左找 小于vo的数来填array[i]
            while (i < j && array[j] >= vo) {
                j--;
            }
            if (i < j) {
                array[i] = array[j];//将array[j]填到array[i]中，array[j]形成了一个新的坑
                i++;
            }
            //从左向右 找 大于vo的数来填array[j]
            while (i < j && array[i] <= vo) {
                i++;
            }
            if (i < j) {
                array[j] = array[i];//将array[i]填到array[i]中，array[i]形成了一个新的坑
                j--;
            }
        }
        //退出时，i==j,将vo填到这个坑中
        array[i] = vo;
        return i;
    }

    public  void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int i = adjustArray(array, left, right);
            quickSort(array, left, i - 1);
            quickSort(array, i + 1, right);
        }
    }

    @Override
    public void sort(int[] array) {
        quickSort(array,0,array.length-1);
    }
}
