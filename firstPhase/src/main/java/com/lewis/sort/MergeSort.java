package com.lewis.sort;

import java.util.Arrays;

/**
 * Created by zhangminghua on 2016/12/5.
 * 归并排序
 */
public class MergeSort implements ISortable{

    @Override
    public void sort(int[] array) {
        int[] tmpArray = new int[array.length];
        mergeSort(array,0,array.length-1,tmpArray);
    }

    private void mergeSort(int[] a,int first,int last,int[] tmp){
        if (first < last) {
            int mid = (first+last)/2;
            mergeSort(a,first,mid,tmp);
            mergeSort(a,mid+1,last,tmp);
            mergeArray(a,first,mid,last,tmp);
        }
    }

    //将a[first..mid] 和a[mid+1 .. last]合并
    private void mergeArray(int[] a,int first,int mid,int last,int[] tmp){
        int leftIndex = first,rightIndex =mid+1,k=0;
        while (leftIndex <= mid && rightIndex <= last) {
            if (a[leftIndex] < a[rightIndex]) {
                tmp[k++] = a[leftIndex++];
            }else{
                tmp[k++] = a[rightIndex++];
            }
        }
        while (leftIndex <= mid) {
            tmp[k++] = a[leftIndex++];
        }
        while (rightIndex <= last) {
            tmp[k++] = a[rightIndex++];
        }
        for (int i = 0; i <k; i++) {
            a[first+i] = tmp[i];
        }
    }

}
