package com.lewis.zk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhangminghua on 2017/1/16.
 */
public class Test {

    public static void main(String[] args) {
        ProducerConsumerDemo1<Request> pc = new ProducerConsumerDemo1<Request>(10);
        AtomicLong counter = new AtomicLong(1);
        List<Thread> threads = new ArrayList<Thread>(10);
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                threads.add(new SendThread("sendThread-" + i, pc, counter));
            } else {
                threads.add(new HandlerThread("handlerThread-" + i, pc));
            }
        }
        for (Thread thread : threads) {
            thread.start();
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static class SendThread extends Thread {
        final ProducerConsumerDemo1<Request> pc;
        final AtomicLong counter;

        SendThread(String name, ProducerConsumerDemo1 pc, AtomicLong count) {
            super(name);
            this.pc = pc;
            this.counter = count;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Request request = new Request("No." + counter.getAndIncrement());
                    pc.put(request);
                    System.out.println(Thread.currentThread().getName() + " put " + request.toString());
                    // TimeUnit.SECONDS.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class HandlerThread extends Thread {
        final ProducerConsumerDemo1<Request> pc;

        HandlerThread(String name, ProducerConsumerDemo1 pc) {
            super(name);
            this.pc = pc;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Request request = pc.take();
                    System.out.println(Thread.currentThread().getName() + " handler request " + request.toString());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Request {
        private String name;

        public Request(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Request:" + name + '\n';
        }
    }
}
