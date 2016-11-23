package com.lewis.multiThreadPattern.guidedSuspension;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2016/11/23.
 */
public class ConditionVarBlocker implements Blocker {
    private final Lock lock;
    private final Condition condition;

    public ConditionVarBlocker(Lock lock) {
        this.lock = lock;
        this.condition = lock.newCondition();
    }

    public ConditionVarBlocker() {
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    @Override
    public <V> V callWithGuard(GuardedAction<V> guardedAction) throws Exception {
        lock.lockInterruptibly();
        try {
            Predicate guard = guardedAction.guard;
            while (!guard.evaluate()) {
                System.out.println("callWithGuard waiting.....");
                condition.await();
            }
            return guardedAction.call();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void singalAfter(Callable<Boolean> stateOperation) throws Exception {
        lock.lockInterruptibly();
        try {
            if (stateOperation.call()) {
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void singal() throws Exception {
        lock.lockInterruptibly();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void broadcastAfter(Callable<Boolean> stateOperation) throws Exception {
        lock.lockInterruptibly();
        try {
            if (stateOperation.call()) {
                condition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
