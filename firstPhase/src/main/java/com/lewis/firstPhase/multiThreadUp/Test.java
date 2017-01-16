package com.lewis.firstPhase.multiThreadUp;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by zhangminghua on 2017/1/16.
 */
public class Test {
    public static void main(String[] args) {
        ProducerConsumerDemo1<Request> pc = new ProducerConsumerDemo1(10);
        AtomicLong counter = new AtomicLong(1);
        Set<Thread> threadSet = IntStream.rangeClosed(1, 10).mapToObj(x -> {
            if (x % 2 == 0) return new SendThread("sendThread-" + x, pc,counter);
            return new HandlerThread("handlerThread-" + x, pc);
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

    static class SendThread extends Thread {
        final ProducerConsumerDemo1<Request> pc;
        final AtomicLong counter;
        SendThread(String name, ProducerConsumerDemo1 pc,AtomicLong count) {
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
