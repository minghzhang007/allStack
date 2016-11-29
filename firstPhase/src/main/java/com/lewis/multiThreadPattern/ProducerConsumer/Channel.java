package com.lewis.multiThreadPattern.ProducerConsumer;

/**
 * Created by Administrator on 2016/11/29.
 * 对通道参与者进行抽象
 */
public interface Channel<P> {

    P take() throws InterruptedException;

    void put(P product)throws InterruptedException;
}
