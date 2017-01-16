package com.lewis.spring.gc;

/**
 * Created by zhangminghua on 2017/1/5.
 * 对象晋升老年代的阀值参数 -XX:MaxTenuringThreshold
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 */
public class GCDemo2 {
    public static final int m = 1024*1024;
    public static void main(String[] args) {

        byte[]  b1,b2,b3;
        b1 = new byte[m/4];
        b2 = new byte[4*m];
        b3 = new byte[4*m];
        b3 = null;
        b3 = new byte[4*m];
    }
}
