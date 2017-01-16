package com.lewis.firstPhase.multiThreadUp;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by zhangminghua on 2017/1/16.
 */
public class ProducerConsumerDemo {

    //mail lock guarding all access
    private static Lock lock = new ReentrantLock();
    //condition for waiting producer put
    private static Condition notFull = lock.newCondition();
    //condition for waiting consumer take
    private static Condition notEmpty = lock.newCondition();

    static ArrayList<String> datas = new ArrayList<String>();

    public static void main(String[] args) {
        Set<Thread> threadSet = IntStream.rangeClosed(1, 10).mapToObj(i -> {
            if (i % 2 == 0) return new Consumer("consumer-" + i);
            return new Producer("producer-" + i);
        }).filter(x -> {
            x.start();
            return true;
        }).collect(Collectors.toSet());
        threadSet.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    static class Consumer extends Thread {
        Consumer(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (ProducerConsumerDemo.datas.isEmpty()) {
                        System.out.println(Thread.currentThread().getName()+" into waiting...");
                        notEmpty.await();
                        System.out.println(Thread.currentThread().getName()+" wait over");
                    }
                    if(ProducerConsumerDemo.datas.isEmpty())
                    {
                        System.out.println("impossible empty !! "+Thread.currentThread().getName());
                        System.exit(-1);
                    }
                    ProducerConsumerDemo.datas.forEach(x->System.out.println(Thread.currentThread().getName()+" consumer:"+x));
                    ProducerConsumerDemo.datas.clear();
                    notFull.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class Producer extends Thread{
        Producer(String name){
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (ProducerConsumerDemo.datas.size() > 10) {
                        System.out.println(Thread.currentThread().getName()+ " into wait...");
                        notFull.await();
                        System.out.println(Thread.currentThread().getName()+ " wait over");
                    }
                    if(ProducerConsumerDemo.datas.size()>10)
                    {
                        System.out.println("impossible full !! "+Thread.currentThread().getName());
                        System.exit(-1);
                    }
                    IntStream.rangeClosed(1,10).forEach(x->ProducerConsumerDemo.datas.add(Thread.currentThread().getName()+" produce: data-"+x+"\n"));
                    notEmpty.signalAll();
                }catch (InterruptedException e){
                    e.printStackTrace();
                } finally{
                    lock.unlock();
                }
            }
        }
    }

}
