package com.lewis.multiThreadPattern.ProducerConsumer;


import java.util.concurrent.BlockingDeque;

/**
 * Created by Administrator on 2016/11/29.
 */
public interface WorkStealingEnabledChannel<P> extends Channel<P>{

    P take(BlockingDeque<P> preferedDeque) throws InterruptedException;
}
