package com.lewis.multiThreadPattern.guidedSuspension;

import com.lewis.multiThreadPattern.TwoPhaseTermination.AlarmType;

/**
 * Created by Administrator on 2016/11/23.
 */
public class AlarmInfo {
    private String id;
    private String extralInfo;
    private AlarmType type;

    public AlarmInfo(String id, AlarmType type) {
        this.id = id;
        this.type = type;
    }

    public AlarmInfo() {
    }

    public AlarmType getType() {
        return type;
    }

    public void setType(AlarmType type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getExtralInfo() {
        return extralInfo;
    }

    public void setExtralInfo(String extralInfo) {
        this.extralInfo = extralInfo;
    }

    @Override
    public String toString() {
        return "AlarmInfo{" +
                "id='" + id + '\'' +
                ", extralInfo='" + extralInfo + '\'' +
                ", type=" + type +
                '}';
    }
}
