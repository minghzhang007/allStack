package com.lewis.spring.gc;

/**
 * Created by zhangminghua on 2017/1/4.
 * -verbose:gc 在控制台输出JVM GC的情况
 * -Xloggc:D:/java/logs/gc.log 将JVM GC情况输出到文件中
 *  -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:+PrintGCDetails
 */
public class GCDemo {
    public static void main(String[] args) {
        int m = 1024 * 1024;
        byte[] b1 = new byte[2 * m];
        byte[] b2 = new byte[2 * m];
        byte[] b3 = new byte[2 * m];
        byte[] b4 = new byte[4 * m];
    }
}
