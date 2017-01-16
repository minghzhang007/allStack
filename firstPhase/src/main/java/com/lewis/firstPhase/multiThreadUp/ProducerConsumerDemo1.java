package com.lewis.firstPhase.multiThreadUp;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by zhangminghua on 2017/1/16.
 */
public class ProducerConsumerDemo1<E> {
    //main lock for guarding all access
    private final ReadWriteLock lock;
    //condition for waiting puts
    private final Condition notFull;
    //condition for waiting taks
    private final Condition notEmpty;

    private final E[] items;

    private int putIndex;

    private int takeIndex;

    private int count;

    public ProducerConsumerDemo1(int size) {
        this.lock = new ReentrantReadWriteLock(false);
        this.notFull = lock.writeLock().newCondition();
        this.notEmpty = lock.writeLock().newCondition();
        this.items = (E[]) new Object[size];
    }

    public E take() throws InterruptedException {
        lock.writeLock().lockInterruptibly();
        try {
            try {
                while (count == 0) {
                    notEmpty.await();
                }
            } catch (InterruptedException e) {
                notEmpty.signal();
                throw e;
            }
            E[] items = this.items;
            E item = items[takeIndex];
            items[takeIndex] = null;
            takeIndex = increase(takeIndex);
            count--;
            notEmpty.signalAll();
            return item;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void put(E e) throws InterruptedException {
        if (e == null) throw new NullPointerException();
        E[] items = this.items;
        lock.writeLock().lockInterruptibly();
        try {
            try {
                while (count == items.length) {
                    notFull.await();
                }
            } catch (InterruptedException ex) {
                notFull.signal();
                throw ex;
            }
            items[putIndex]=e;
            putIndex = increase(putIndex);
            count++;
            notEmpty.signalAll();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public E[] getItems() throws InterruptedException{
        lock.readLock().lockInterruptibly();
        try {
            E[] items = this.items;
            return items;
        } finally {
            lock.readLock().unlock();
        }
    }

    private int increase(int takeIndex) {
        return ++takeIndex == items.length ? 0 : takeIndex;
    }
}
