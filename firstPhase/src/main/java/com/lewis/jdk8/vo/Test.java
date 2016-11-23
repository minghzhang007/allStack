package com.lewis.jdk8.vo;

import java.util.stream.Stream;

/**
 * Created by zhangminghua on 2016/11/22.
 */
public class Test {
    public static void main(String[] args) {
        //Stream.iterate(0,n->n+2).limit(10).forEach(System.out::println);
        //输出斐波那契数列
        Stream.iterate(new int[]{0,1},t->new int[]{t[1],t[0]+t[1]}).map(t->t[0]).limit(20).forEach(System.out::println);

    }
}
