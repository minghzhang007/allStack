package com.lewis.multiThreadPattern.TwoPhaseTermination;

import com.lewis.multiThreadPattern.guidedSuspension.AlarmAgent;
import com.lewis.multiThreadPattern.guidedSuspension.AlarmInfo;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/11/24.
 * 模式角色：Two-phaseTermination.ConcreteTerminatableThead
 */
public class AlarmSendingThread extends AbsTerminatableThread {

    private final AlarmAgent alarmAgent = new AlarmAgent();
    //告警队列
    private final BlockingQueue<AlarmInfo> alarmQueue;

    private final ConcurrentMap<String,AtomicInteger> submittedAlarmRegistry;

    public AlarmSendingThread(){
        alarmQueue = new ArrayBlockingQueue<AlarmInfo>(100);
        submittedAlarmRegistry = new ConcurrentHashMap<>();
    }

    @Override
    protected void doRun() throws Exception {
        AlarmInfo alarm;
        TimeUnit.MILLISECONDS.sleep(1000);
        alarm = alarmQueue.take();
        terminationToken.reservations.decrementAndGet();
        try {
            //将告警信息发送至告警服务器
            alarmAgent.sendAlarm(alarm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //处理恢复告警：将相应的故障告警从注册表中删除，
        //使得响应故障恢复后若再次出现相同的故障，该故障信息能够上报到服务器
        if (AlarmType.RESUME == alarm.getType()) {
            String key = AlarmType.FAULT.toString()+":"+alarm.getId()+"@"+alarm.getExtralInfo();
            submittedAlarmRegistry.remove(key);
            key = AlarmType.RESUME.toString()+":"+alarm.getId()+"@"+alarm.getExtralInfo();
            submittedAlarmRegistry.remove(key);
        }
    }

    public int sendAlarm(AlarmInfo alarmInfo){
        AlarmType type = alarmInfo.getType();
        String id = alarmInfo.getId();
        String extralInfo = alarmInfo.getExtralInfo();
        if (terminationToken.isToShutdown()) {
            //记录告警
            System.err.println("rejected alarm:"+id+","+extralInfo);
            return -1;
        }
        int duplicateSubmissionCount = 0;
        try {
            AtomicInteger prevSubmittedCounter;
            prevSubmittedCounter=submittedAlarmRegistry.putIfAbsent(type.toString()+":"+id+"@"+extralInfo,new AtomicInteger(0));
            if (null == prevSubmittedCounter) {
                terminationToken.reservations.incrementAndGet();
                alarmQueue.put(alarmInfo);
            }else{
                // 故障未恢复，不用重复发送告警信息给服务器，仅仅增加计数
                duplicateSubmissionCount= prevSubmittedCounter.incrementAndGet();
            }
        } catch (Exception e) {

        }
        return duplicateSubmissionCount;
    }

    protected void doCleanUp(Exception cause){
        if (null != cause && !(cause instanceof InterruptedException)) {
            cause.printStackTrace();
        }
        alarmAgent.disconnect();
    }
}
