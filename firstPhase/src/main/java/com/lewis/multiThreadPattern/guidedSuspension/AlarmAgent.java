package com.lewis.multiThreadPattern.guidedSuspension;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2016/11/23.4
 * 负责连接告警服务器，并发送告警信息到告警服务器
 */
public class AlarmAgent{
    private static AlarmAgent alarm = new AlarmAgent();

    public static AlarmAgent getAlarmAgent(){
        return alarm;
    }

    //用于记录AlarmAgent是否连接上告警服务器
    private volatile boolean connectedToServer = false;

    //模式角色：GuardedSuspension.Predicate
    private final Predicate agentConnected = new Predicate() {
        @Override
        public boolean evaluate() {
            return connectedToServer;
        }
    };

    //模式角色：GuardedSuspension.Blocker
    private final Blocker blocker = new ConditionVarBlocker();

    //定时心跳器
    private final Timer heartbeatTimer = new Timer(true);

    //通过网络连接将告警信息发送到告警服务器
    private void doSendAlarm(AlarmInfo alarmInfo){
        System.out.println("sending alarm "+alarmInfo);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //发送告警信息
    public void sendAlarm(AlarmInfo alarmInfo)throws Exception{
        //可能需要等待，直到AlarmAgent连接上告警服务器（或者连接中断后重新连上服务器）
        //模式角色：GuardedSuspension.GuardedAction
        GuardedAction<Void> guardedAction = new GuardedAction<Void>(agentConnected) {

            @Override
            public Void call() throws Exception {
                doSendAlarm(alarmInfo);
                return null;
            }
        };
        blocker.callWithGuard(guardedAction);
    }

    public void init(){
        Thread connectingThread = new Thread(new ConnectingTask());
        connectingThread.start();

        heartbeatTimer.schedule(new HeartbeatTask(),6000,2000);
    }

    public void disconnect(){
        System.out.println("disconnected from alarm server.");
        connectedToServer =false;
    }

    protected void onConnected(){
        try {
            blocker.broadcastAfter(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    connectedToServer = true;
                    System.out.println("connected to server.");
                    System.out.println("state connectedToServer is "+connectedToServer);
                    return Boolean.TRUE;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDisconnected(){
        connectedToServer = false;
    }

    //负责与告警服务器建立网络连接
    private class ConnectingTask implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onConnected();
        }
    }

    //心跳定时任务：定时检查与告警服务器的连接是否正常，发现连接异常后自动重新连接
    private class HeartbeatTask extends TimerTask{

        @Override
        public void run() {
            if (!testConnection()) {
                onDisconnected();
                reconnect();
            }
        }

        private boolean testConnection(){
            System.out.println("testConnection...");
            return true;
        }

        private void reconnect(){
            System.out.println("reconnect...");
            ConnectingTask connectingTask = new ConnectingTask();
            //直接在心跳定时器线程中执行
            connectingTask.run();
        }
    }
}
