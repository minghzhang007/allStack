package com.lewis.firstPhase.io.advanceSecondTopic;

/**
 * Created by zhangminghua on 2016/12/14.
 */
public class Record implements Comparable<Record>{
    public Record(long totalSalary, int count, String namePre) {
        this.totalSalary = totalSalary;
        this.count = count;
        this.namePre = namePre;
    }

    private long totalSalary;

    private int count;

    private String namePre;

    public long getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(long totalSalary) {
        this.totalSalary = totalSalary;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNamePre() {
        return namePre;
    }

    public void setNamePre(String namePre) {
        this.namePre = namePre;
    }

    @Override
    public int compareTo(Record o) {
        return Long.compare(o.getTotalSalary(),this.getTotalSalary());
    }
}
