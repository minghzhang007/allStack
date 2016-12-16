package com.lewis.firstPhase.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

/**
 * Created by Administrator on 2016/12/13.
 */
public class Main {
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        Long numberCount = 20000000L;
        long l = forkJoinSum(numberCount);
        System.out.println(l);
        System.out.println("t:"+(System.currentTimeMillis()-t1));
    }

    public static long forkJoinSum(long n){
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        long sum =0;
        for (int i = 0; i < numbers.length; i++) {
            sum +=numbers[i];
        }
        System.out.println("sum:"+sum);
        ForkJoinSumCalculator task = new ForkJoinSumCalculator(numbers);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Long result = forkJoinPool.invoke(task);
        return result;
    }
}
