package com.lewis.firstPhase.functional;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by Administrator on 2016/12/25.
 */
public class FunctionalDemo {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("ok");
        Predicate<String> isKnonw =  set::contains;
        boolean ok = isKnonw.test("ok");
        System.out.println(ok);
    }
}
