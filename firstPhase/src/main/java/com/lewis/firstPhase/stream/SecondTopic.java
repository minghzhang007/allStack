package com.lewis.firstPhase.stream;

import java.util.stream.Stream;

/**
 * Created by zhangminghua on 2016/12/29.
 * 2 下面代码为什么输出流中的每个元素2遍
 Stream.of("d2", "a2", "b1", "b3", "c")
 .filter(s -> {
 System.out.println("filter: " + s);
 return true;
 })
 .forEach(s -> System.out.println("forEach: " + s));
 */

/**
 * filter方法接收一个Predicate，里面返回true,意思是stream流里面所有的元素都不会被过滤，打印filter: X ，X依次是流中的各个元素。
 * 使用终端操作forEach方法，里面打印出流中的每个元素forEach: X
 *
 *
 */
public class SecondTopic {
    public static void main(String[] args) {
        Stream.of("d2","a2","b1","b3","c").filter(s -> {
            System.out.println("filter: "+s);
            return true;
        }).forEach(s -> System.out.println("forEach: "+s));
    }
}
