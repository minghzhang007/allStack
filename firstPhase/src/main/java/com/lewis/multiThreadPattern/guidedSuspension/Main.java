package com.lewis.multiThreadPattern.guidedSuspension;

/**
 * Created by Administrator on 2016/11/23.
 */
public class Main {
    private static AlarmAgent alarmAgent =   AlarmAgentFactory.getAlarmAgent();
    public static AlarmAgent getInstance(){
        return alarmAgent;
    }
    public static void main(String[] args) {
        try {
            System.out.println(alarmAgent.toString());
            alarmAgent.sendAlarm(new AlarmInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
