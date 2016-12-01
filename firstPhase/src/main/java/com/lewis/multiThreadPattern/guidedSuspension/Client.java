package com.lewis.multiThreadPattern.guidedSuspension;

/**
 * Created by Administrator on 2016/11/23.
 */
public class Client {
    private static AlarmAgent alarmAgent =   AlarmAgentFactory.getAlarmAgent();
    public static AlarmAgent getInstance(){
        return alarmAgent;
    }
    public static void main(String[] args) {
        alarmAgent.init();
        System.out.println(alarmAgent.toString());
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
