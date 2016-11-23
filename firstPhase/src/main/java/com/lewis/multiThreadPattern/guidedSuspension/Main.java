package com.lewis.multiThreadPattern.guidedSuspension;

/**
 * Created by Administrator on 2016/11/23.
 */
public class Main {
    public static void main(String[] args) {
        AlarmAgent alarmAgent = AlarmAgentFactory.getAlarmAgent();
        try {
           new Thread(){
               @Override
               public void run() {
                   try {
                       Thread.sleep(5000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   System.out.println("will init...");
                  // alarmAgent.init();
               }
           }.start();
            alarmAgent.sendAlarm(new AlarmInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
