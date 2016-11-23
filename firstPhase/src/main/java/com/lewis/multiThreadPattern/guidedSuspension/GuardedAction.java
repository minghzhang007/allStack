package com.lewis.multiThreadPattern.guidedSuspension;

import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2016/11/23.
 */
public abstract class GuardedAction<V> implements Callable<V> {

    protected Predicate guard;

    public GuardedAction(Predicate predicate){
        this.guard = predicate;
    }
}
