package com.lewis.executor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author zmh46712
 * @version Id: Demo1, v 0.1 2017/4/28 10:17 zmh46712 Exp $
 */
public class Demo1 {

    public static void main(String[] args) {
        ThreadPoolExecutor executorService = getSingleThreadExecutor();

        IntStream.rangeClosed(1, 10).forEach(x ->
                executorService.submit(() -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("++" + x);
                    int i = 1 / 0;
                    System.out.println("--" + x);
                })
        );

        IntStream.rangeClosed(1, 10).forEach(x -> {
            System.out.println("ActiveCount: " + executorService.getActiveCount());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static ThreadPoolExecutor getSingleThreadExecutor() {
        AtomicInteger number = new AtomicInteger(1);
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r, "mythread-" + number.getAndIncrement()));
    }
}
