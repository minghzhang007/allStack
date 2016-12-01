package com.lewis.jdk8.defaultMethod;

/**
 * Created by zhangminghua on 2016/11/26.
 */
public interface IA {
    default void hello(){
        System.out.println("Hello from IA.");
    }
}
