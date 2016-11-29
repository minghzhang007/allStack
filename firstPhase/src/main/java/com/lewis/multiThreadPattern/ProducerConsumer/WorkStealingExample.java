package com.lewis.multiThreadPattern.ProducerConsumer;

import com.lewis.multiThreadPattern.TwoPhaseTermination.AbsTerminatableThread;
import com.lewis.multiThreadPattern.TwoPhaseTermination.TerminationToken;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Administrator on 2016/11/29.
 * 工作窃取算法示例：
 */
public class WorkStealingExample {

    private final WorkStealingEnabledChannel<String> channel;
    private final TerminationToken token = new TerminationToken();

    public WorkStealingExample() {
        int nCPU = Runtime.getRuntime().availableProcessors();
        int consumerCount = nCPU / 2 + 1;
        LinkedBlockingDeque[] managedQueues = new LinkedBlockingDeque[consumerCount];
        channel = new WorkStealingChannel<String>(managedQueues);
        Consumer[] consumers = new Consumer[consumerCount];
        for (int i = 0; i < consumerCount; i++) {
            managedQueues[i] = new LinkedBlockingDeque<String>();
            consumers[i] = new Consumer(token,managedQueues[i]);
        }
        for (int i = 0; i < nCPU; i++) {
            new Producer().start();
        }
        for (int i = 0; i < consumerCount; i++) {
            consumers[i].start();
        }
    }

    public void doSomething(){

    }

    public static void main(String[] args) {
        WorkStealingExample wse = new WorkStealingExample();
        wse.doSomething();
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class Producer extends AbsTerminatableThread {
        private int i = 0;

        @Override
        protected void doRun() throws Exception {
            channel.put(String.valueOf(i++));
            token.reservations.incrementAndGet();
        }
    }

    private class Consumer extends AbsTerminatableThread{
        private final BlockingDeque<String> workQueue;

        public Consumer(TerminationToken terminationToken, BlockingDeque<String> workQueue) {
            super(terminationToken);
            this.workQueue = workQueue;
        }

        @Override
        protected void doRun() throws Exception {
            //
            String product = channel.take(workQueue);
            System.out.println("Processing product:"+product);
            try {
                Thread.sleep(new Random().nextInt(50));
            } finally {
                token.reservations.decrementAndGet();
            }
        }
    }
}
