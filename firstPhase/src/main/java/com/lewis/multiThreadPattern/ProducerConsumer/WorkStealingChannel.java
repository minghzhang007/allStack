package com.lewis.multiThreadPattern.ProducerConsumer;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2016/11/29.
 */
public class WorkStealingChannel<T> implements WorkStealingEnabledChannel<T> {
    //受管队列
    private final BlockingDeque<T>[] managedQueues;

    public WorkStealingChannel(BlockingDeque<T>[] managedQueues) {
        this.managedQueues = managedQueues;
    }

    @Override
    public T take() throws InterruptedException {
        return take(null);
    }

    @Override
    public void put(T product) throws InterruptedException {
        int targetIndex=(product.hashCode()%managedQueues.length);
        BlockingQueue<T> targetQueue=managedQueues[targetIndex];
        targetQueue.put(product);
    }

    @Override
    public T take(BlockingDeque<T> preferedDeque) throws InterruptedException {
        BlockingDeque<T> targetDeque = preferedDeque;
        T product = null;
        //试图从指定的队列首取"产品"
        if (null != targetDeque) {
            product = targetDeque.poll();
        }
        int queueIndex = -1;
        while (null == product) {
            queueIndex = (queueIndex + 1) % managedQueues.length;
            targetDeque = managedQueues[queueIndex];
            //试图从其他受管队列的队尾“窃取产品”
            product = targetDeque.pollLast();
            if (preferedDeque == targetDeque) {
                break;
            }
        }
        if (null == product) {
            //随机“窃取”其他受管队列的“产品”
            queueIndex = (int)(System.currentTimeMillis()%managedQueues.length);
            targetDeque=managedQueues[queueIndex];
            product = targetDeque.takeLast();
            System.out.println("Stealed from "+queueIndex+":"+product);
        }
        return product;
    }
}
