package com.lewis.multiThreadPattern.ProducerConsumer;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2016/11/29.
 * 基于阻塞队列的通道实现
 */
public class BlockingQueueChannel<P> implements Channel<P>{

    private final BlockingQueue<P> queue;

    public BlockingQueueChannel(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public P take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public void put(P product) throws InterruptedException {
        queue.put(product);
    }
}
