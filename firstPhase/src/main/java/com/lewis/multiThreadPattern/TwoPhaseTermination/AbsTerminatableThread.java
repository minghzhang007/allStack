package com.lewis.multiThreadPattern.TwoPhaseTermination;

/**
 * Created by Administrator on 2016/11/24.
 * 可停止的抽象线程
 */
public abstract class AbsTerminatableThread extends Thread implements Terminatable {

    //模式角色：Two-phaseTermination.TerminationToken
    public final TerminationToken terminationToken;

    public AbsTerminatableThread() {
        this(new TerminationToken());
    }

    /**
     * 线程间共享的线程终止标志实例
     *
     * @param terminationToken
     */
    public AbsTerminatableThread(TerminationToken terminationToken) {
        super();
        this.terminationToken = terminationToken;
        terminationToken.register(this);
    }

    /**
     * 留给子类实现其线程处理逻辑
     *
     * @throws Exception
     */
    protected abstract void doRun() throws Exception;

    /**
     * 里给子类实现；用于实现线程停止后的一些清理动作
     *
     * @param cause
     */
    protected void doCleanUp(Exception cause) {

    }

    /**
     * 留给子类实现：用于执行线程停止所需要的操作
     */
    protected void doTerminate() {

    }

    @Override
    public void run() {
        Exception ex = null;
        try {
            for (; ; ) {
                //在执行线程的处理逻辑前，先判断线程停止的标志
                if (terminationToken.isToShutdown() && terminationToken.reservations.get() <= 0) {
                    break;
                }
                doRun();
            }
        } catch (Exception e) {
            //使得线程能够响应interrupt调用而退出
            ex = e;
        } finally {
            try {
                doCleanUp(ex);
            } finally {
                terminationToken.notifyThreadTermination(this);
            }
        }
    }

    @Override
    public void interrupt() {
        terminate();
    }

    //请求停止线程
    @Override
    public void terminate() {
        terminationToken.setToShutdown(true);
        try {
            doTerminate();
        } finally {
            //若无待处理的任务，则试图强制终止线程
            if (terminationToken.reservations.get() <= 0) {
                super.interrupt();
            }
        }
    }

    public void terminate(boolean waitUtilThreadTerminated) {
        terminate();
        if (waitUtilThreadTerminated) {
            try {
                this.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
