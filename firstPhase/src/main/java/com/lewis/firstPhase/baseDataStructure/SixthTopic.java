package com.lewis.firstPhase.baseDataStructure;

import com.lewis.sort.ArrayFactory;
import sun.rmi.runtime.Log;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by zhangminghua on 2016/12/3.
 * 6题
 * Arrays.parallelSort在数组超过多少时候才开启并行排序？采用位运算，给出推导过程
 *
 *     public static void parallelSort(byte[] a) {
 *       int n = a.length, p, g;
 *      if (n <= MIN_ARRAY_SORT_GRAN ||
 *       (p = ForkJoinPool.getCommonPoolParallelism()) == 1)
 *       DualPivotQuicksort.sort(a, 0, n - 1);
 *       else
 *       new ArraysParallelSortHelpers.FJByte.Sorter
 *       (null, a, new byte[n], 0, n, 0,
 *       ((g = n / (p << 2)) <= MIN_ARRAY_SORT_GRAN) ?
 *       MIN_ARRAY_SORT_GRAN : g).invoke();
 * 从Arrays.parallelSort方法的源码可以知道，
 * 在数组元素个数超超过int MIN_ARRAY_SORT_GRAN = 1 << 13;时才会开启并行排序，
 * 1<<13的数值为8192
 */
public class SixthTopic {

    public static void main(String[] args) {
        int[] intArray = ArrayFactory.createIntArray(1 << 19, 20000);
        int[] newArray = ArrayFactory.createIntArray(1 << 22, 20000);
        int[] intArrays = Arrays.copyOf(intArray, intArray.length);
        int[] newArrays = Arrays.copyOf(newArray, newArray.length);
        long beginTime = System.currentTimeMillis();
        Arrays.parallelSort(intArray);
        System.out.println("parallelSort 1<<19 costTime #"+(System.currentTimeMillis()-beginTime));
        beginTime = System.currentTimeMillis();
        Arrays.parallelSort(newArray);
        System.out.println("parallelSort 1 << 22 costTime #"+(System.currentTimeMillis()-beginTime));
        System.out.println(Runtime.getRuntime().availableProcessors());
        beginTime = System.currentTimeMillis();
        Arrays.sort(intArrays);
        System.out.println("sort 1<<19 costTime#"+(System.currentTimeMillis()-beginTime));
        beginTime = System.currentTimeMillis();
        Arrays.sort(newArrays);
        System.out.println("sort 1<<22 costTime#"+(System.currentTimeMillis()-beginTime));
    }

   /*
     private static final int MIN_ARRAY_SORT_GRAN = 1 << 13;

    public static void parallelSort(byte[] a) {
        int n = a.length, p, g;
        if (n <= MIN_ARRAY_SORT_GRAN ||
                (p = ForkJoinPool.getCommonPoolParallelism()) == 1)
            DualPivotQuicksort.sort(a, 0, n - 1);
        else
            new ArraysParallelSortHelpers.FJByte.Sorter
                    (null, a, new byte[n], 0, n, 0,
                            ((g = n / (p << 2)) <= MIN_ARRAY_SORT_GRAN) ?
                                    MIN_ARRAY_SORT_GRAN : g).invoke();
    }
*/
}
