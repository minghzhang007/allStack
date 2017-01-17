package com.lewis.firstPhase.jmm;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2017/1/10.
 * 3 编程验证normal var ,volaitle，synchronize,atomicLong ,LongAdder，这几种做法实现的计数器方法，在多线程情况下的性能，准确度
 * <p>
 * class MyCounter
 * {
 * private long value;//根据需要进行替换
 * public void incr();
 * public long getCurValue();//得到最后结果
 * }
 * 启动10个线程一起执行，每个线程调用incr() 100万次，
 * 所有线程结束后，打印 getCurValue()的结果，分析程序的结果 并作出解释。 用Stream和函数式编程实现则加分！
 */
public class ThirdTopic {
    private static ExecutorService executorService;

    static {
        int nCPU = Runtime.getRuntime().availableProcessors();
        BlockingQueue<Runnable> watingQueue = new LinkedBlockingDeque<>();
        ThreadFactory threadFactory = new ThreadFactory() {
            private AtomicInteger count = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "worker-" + count.getAndIncrement());
                return thread;
            }
        };
        executorService = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, watingQueue, threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("线程池关闭");
                if (executorService != null) {
                    try {
                        executorService.shutdown();
                        executorService.awaitTermination(5, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        //normal();
        advance();
    }

    private static void advance() {
        long begin = System.currentTimeMillis();
        int count = 100 * 10000;
        NormalVarMyCount normalVarMyCount = new NormalVarMyCount();
        IntStream.range(1, 10 * count).filter(i -> {
            executorService.submit(() -> normalVarMyCount.incr());
            return true;
        }).count();
        System.out.println("normal value sum  :" + normalVarMyCount.getCurValue());
        System.out.println("normal cost:" + (System.currentTimeMillis() - begin));

    }

    private static void normal() {
        long begin = System.currentTimeMillis();
        int count = 100 * 10000;
        NormalVarMyCount normalVarMyCount = new NormalVarMyCount();
        Runnable normalR = new Runnable() {
            @Override
            public void run() {
                normalVarMyCount.incr();
            }
        };
        for (int i = 0; i < 10 * count; i++) {
            executorService.submit(normalR);
        }
        System.out.println("normal value sum  :" + normalVarMyCount.getCurValue());
        System.out.println("normal cost:" + (System.currentTimeMillis() - begin));
        begin = System.currentTimeMillis();
        VolatileVarMyCount volatileVarMyCount = new VolatileVarMyCount();
        Runnable volatileR = new Runnable() {
            @Override
            public void run() {
                volatileVarMyCount.incr();
            }
        };
        for (int i = 0; i < 10 * count; i++) {
            executorService.submit(volatileR);
        }
        System.out.println("Volatile value sum  :" + volatileVarMyCount.getCurValue());
        System.out.println("Volatile cost:" + (System.currentTimeMillis() - begin));

        begin = System.currentTimeMillis();
        SynVarMyCount synVarMyCount = new SynVarMyCount();
        Runnable synR = new Runnable() {
            @Override
            public void run() {
                synVarMyCount.incr();
            }
        };
        for (int i = 0; i < 10 * count; i++) {
            executorService.submit(synR);
        }
        System.out.println("synchronized value sum  :" + synVarMyCount.getCurValue());
        System.out.println("synchronized cost:" + (System.currentTimeMillis() - begin));

        begin = System.currentTimeMillis();
        AtomicVarMyCount atomicVarMyCount = new AtomicVarMyCount();
        Runnable atomicR = new Runnable() {
            @Override
            public void run() {
                atomicVarMyCount.incr();
            }
        };
        for (int i = 0; i < 10 * count; i++) {
            executorService.submit(atomicR);
        }
        System.out.println("atomic value sum  :" + atomicVarMyCount.getCurValue());
        System.out.println("atomic cost:" + (System.currentTimeMillis() - begin));

        begin = System.currentTimeMillis();
        LongAdderVarMyCount longAdderVarMyCount = new LongAdderVarMyCount();
        Runnable longAdderR = new Runnable() {
            @Override
            public void run() {
                longAdderVarMyCount.incr();
            }
        };
        for (int i = 0; i < 10 * count; i++) {
            executorService.submit(longAdderR);
        }
        System.out.println("longAdder value sum  :" + longAdderVarMyCount.getCurValue());
        System.out.println("longAdder cost:" + (System.currentTimeMillis() - begin));
    }

    static class NormalVarMyCount {
        private long value;

        public void incr() {
            value++;
        }

        public long getCurValue() {
            return value;
        }
    }

    static class VolatileVarMyCount {
        private volatile long value;

        public void incr() {
            value++;
        }

        public long getCurValue() {
            return value;
        }
    }

    static class SynVarMyCount {
        private long value;

        public synchronized void incr() {
            value++;
        }

        public synchronized long getCurValue() {
            return value;
        }
    }

    static class AtomicVarMyCount {
        private AtomicLong value = new AtomicLong(0);

        public void incr() {
            value.getAndIncrement();
        }

        public long getCurValue() {
            return value.get();
        }
    }

    static class LongAdderVarMyCount {
        private LongAdder value = new LongAdder();

        public void incr() {
            value.increment();
        }

        public long getCurValue() {
            return value.longValue();
        }
    }
}
