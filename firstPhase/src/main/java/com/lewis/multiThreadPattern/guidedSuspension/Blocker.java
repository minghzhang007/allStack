package com.lewis.multiThreadPattern.guidedSuspension;

import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2016/11/23.
 */
public interface Blocker {

    /**
     * 在保护条件成立的条件下，执行目标动作；否则阻塞当前线程，知道保护条件成立
     * @param guardedAction 带有保护条件的目标动作
     * @return
     * @throws Exception
     */
    <V> V callWithGuard(GuardedAction<V> guardedAction) throws Exception;

    /**
     * 执行stateOperation所指定的目标动作之后，决定是否唤醒本Blocker所暂挂的所有线程中的一个线程
     * @param stateOperation 更改状态的操作，其call方法的返回值为true时，该方法才会唤醒被暂挂的线程
     * @throws Exception
     */
    void singalAfter(Callable<Boolean> stateOperation) throws Exception;

    void singal() throws Exception;

    /**
     * 执行stateOperation所指定的目标动作之后，决定是否唤醒本Blocker所暂挂的所有线程
     * @param stateOperation 更改状态的操作，其call方法的返回值为true时，该方法才会唤醒被暂挂的线程
     * @throws Exception
     */
    void broadcastAfter(Callable<Boolean> stateOperation) throws Exception;
 }
