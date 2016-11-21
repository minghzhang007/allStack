package com.lewis.firstPhase.multiThreadUp;

/**
 * Created by Administrator on 2016/11/21.
 * 生成者生产完数据后要通知消费者；消费者在消费完数据后要通知生产者；要加上这一点notify(),notifyAll()
 */
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestOnly {
    static Object lock=new Object();
    static ArrayList<String> datas=new  ArrayList<String>();

    public static void main(String[] args) throws InterruptedException
    {
        List<Thread> threads= IntStream.range(1, 10).mapToObj(i->{if(i%2==0) {return new ConsumerThread("consumer "+i);} else return new ProducerThread("producer "+i);})
                .filter(t->{t.start();return true;}).collect(Collectors.toList());
        threads.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {

            }
        });

    }
}

class ConsumerThread extends Thread
{

    public ConsumerThread(String string) {
        this.setName(string);
    }

    public void run()
    {
        while(true)
        {
            synchronized(TestOnly.lock)
            {
                if(TestOnly.datas.isEmpty())
                {
                    System.out.println(Thread.currentThread().getName()+" into wait ,because empty ");
                    try {
                        TestOnly.lock.wait();
                    } catch (InterruptedException e) {
                        break;

                    }
                    System.out.println(Thread.currentThread().getName()+" wait finished ");
                }
                if(TestOnly.datas.isEmpty())
                {

                    System.out.println("impossible empty !! "+Thread.currentThread().getName());
                    System.exit(-1);
                }
                TestOnly.datas.forEach(s->System.out.println(s));
                TestOnly.datas.clear();
                //add notifyAll()
                TestOnly.lock.notifyAll();

            }
        }
    }
}

class ProducerThread extends Thread
{
    public ProducerThread(String string) {
        this.setName(string);
    }

    public void run()
    {
        while(true)
        {
            synchronized(TestOnly.lock)
            {
                if(TestOnly.datas.size()>1)
                {
                    System.out.println(Thread.currentThread().getName()+" into wait,because full ");
                    try {
                        TestOnly.lock.wait();
                    } catch (InterruptedException e) {
                        break;

                    }
                    System.out.println(Thread.currentThread().getName()+"wait finished ");
                }
                if(TestOnly.datas.size()>1)
                {

                    System.out.println("impossible full !! "+Thread.currentThread().getName());
                    System.exit(-1);
                }
                IntStream.range(0, 1).forEach(i->TestOnly.datas.add(i+" data"));
                //add notifyAll()
                TestOnly.lock.notifyAll();
            }
        }
    }
}
