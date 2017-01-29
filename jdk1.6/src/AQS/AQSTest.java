package AQS;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/1/25.
 */
public class AQSTest {

    private static Map<Integer, String> map = new HashMap<Integer, String>(16);

    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock(false);
        Thread t2 = new Thread("producer"){
            @Override
            public void run() {
                lock.lock();
                try {
                    while (true) {
                        map.put(1,"name_"+1);
                    }
                    /*for (int i = 0; i < 10; i++) {
                        map.put(i,"name_"+i);
                        TimeUnit.SECONDS.sleep(10);
                    }*/
                }finally {
                    lock.unlock();
                }
            }
        };
        t2.start();
        TimeUnit.MILLISECONDS.sleep(50);
        Thread t1 = new Thread("consumer") {
            @Override
            public void run() {
                lock.lock();
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("consumer:"+i+ " "+map.toString());
                        TimeUnit.SECONDS.sleep(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
