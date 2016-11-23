package com.lewis.multiThreadPattern.guidedSuspension;

/**
 * Created by Administrator on 2016/11/23.
 */
public class AlarmAgentFactory {

    private static AlarmAgent alarmAgent = new AlarmAgent();

    public static AlarmAgent getAlarmAgent(){
        return alarmAgent;
    }
}
