package com.lewis.firstPhase.baseDataStructure;

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
        System.out.println(1<<13);
        for (int i = 1; i <= 13; i++) {
            System.out.println("i<< "+i+" 值为："+ (1<<i));
        }
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
