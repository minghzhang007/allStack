package com.lewis.multiThreadPattern.TwoPhaseTermination;

/**
 * Created by Administrator on 2016/11/26.
 */
public enum AlarmType {
    RESUME("resume"),FAULT("fault");
    private final String name;

    AlarmType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
