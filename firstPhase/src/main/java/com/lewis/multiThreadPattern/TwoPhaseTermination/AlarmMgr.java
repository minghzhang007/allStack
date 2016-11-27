package com.lewis.multiThreadPattern.TwoPhaseTermination;

import com.lewis.multiThreadPattern.guidedSuspension.AlarmInfo;
import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;

/**
 * Created by Administrator on 2016/11/24.
 */
public class AlarmMgr {
    private static final AlarmMgr instance = new AlarmMgr();

    private volatile boolean shutdownRequested = false;
    //告警发送线程
    private final AlarmSendingThread alarmSendingThread;

    private AlarmMgr() {
        alarmSendingThread = new AlarmSendingThread();
    }

    public static AlarmMgr getInstance() {
        return instance;
    }

    /**
     * 发送告警
     * @param type 告警类型
     * @param id 告警编号
     * @param extraInfo 告警参数
     * @return 由type+id+extraInfo唯一确定的告警信息被提交的次数。 -1表示告警管理器已被关闭
     */
    public int sendAlarm(AlarmType type, String id, String extraInfo) {
        System.out.println("Trigger alarm " + type + "," + id + "," + extraInfo);
        int duplicateSubmissionCount = 0;
        try {
            AlarmInfo alarmInfo = new AlarmInfo(id, type);
            alarmInfo.setExtralInfo(extraInfo);
            duplicateSubmissionCount = alarmSendingThread.sendAlarm(alarmInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duplicateSubmissionCount;
    }

    public void init(){
        alarmSendingThread.start();
    }

    public synchronized void shutdown(){
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested!");
        }
        alarmSendingThread.terminate();
        shutdownRequested = true;
    }

}
