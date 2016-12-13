package com.lewis.firstPhase.io.advanceSecondTopic;

/**
 * Created by zhangminghua on 2016/12/13.
 */
public class StartEndIndexPair {
    long startIndex;
    long endIndex;

    public StartEndIndexPair(long startIndex, long endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    public long getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(long endIndex) {
        this.endIndex = endIndex;
    }

    @Override
    public String toString() {
        return "StartEndIndexPair{" +
                "startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                '}';
    }
}
