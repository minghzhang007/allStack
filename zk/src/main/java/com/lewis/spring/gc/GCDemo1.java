package com.lewis.spring.gc;

/**
 * Created by zhangminghua on 2017/1/5.
 * JVM 在server模式下默认的垃圾收集器为：新生代Parallel Scavenge    老年代 Parallel Old
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:PretenureSizeThreshold=3145728 -XX:+UseSerialGC
 * 大对象直接进入老年代 -XX:PretenureSizeThreshold参数：令大于这个参数值的对象直接在老年代分配，该参数只对Serial和ParNew两个收集器有效
 */
public class GCDemo1 {
    public static void main(String[] args) {
        int m = 1024*1024;
        byte[] b1 ;
        b1 = new byte[4*m];
    }
}
