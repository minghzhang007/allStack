package com.lewis.multiThreadPattern.TwoPhaseTermination;

import com.alibaba.fastjson.JSON;
import com.lewis.multiThreadPattern.guidedSuspension.AlarmInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangminghua on 2016/11/28.
 */
public class Main {

    public static void main(String[] args) {
        AlarmMgr instance = AlarmMgr.getInstance();
        instance.init();
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance.sendAlarm(AlarmType.FAULT,String.valueOf(i),"extraInfo"+i);
        }
        System.out.println("send shutdown info.....");
        instance.shutdown();
        Map<String,Map<String,AlarmInfo>> map1 = new HashMap<>();

        Map<String,AlarmInfo> map = new HashMap<String,AlarmInfo>();
        AlarmInfo alarmInfo = new AlarmInfo("100",AlarmType.FAULT);
        map.put("1",alarmInfo);
        alarmInfo = new AlarmInfo("200",AlarmType.RESUME);
        map.put("12",alarmInfo);
        map1.put("journeyDetails",map);
        System.out.println(JSON.toJSONString(map1));
        Date x = new Date();
        System.out.println("now is "+x);
        System.out.println("now is "+x);
        System.out.println("now is "+x);

    }
}
