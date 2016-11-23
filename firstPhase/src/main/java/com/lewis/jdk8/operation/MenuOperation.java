package com.lewis.jdk8.operation;

import com.lewis.jdk8.vo.Dish;
import com.lewis.jdk8.vo.VirtualDB;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by zhangminghua on 2016/11/22.
 */
public class MenuOperation {
    public static void main(String[] args) {
        List<Dish> menus = VirtualDB.getMenus();
        System.out.println(menus.stream().count());
        System.out.println(menus.stream().collect(Collectors.counting()));
        Optional<Dish> collect = menus.stream().collect(Collectors.maxBy(Comparator.comparing(Dish::getCalories)));
        Integer collect1 = menus.stream().collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(collect1);
        System.out.println(menus.stream().collect(Collectors.averagingInt(Dish::getCalories)));
        IntSummaryStatistics collect2 = menus.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(collect2);
        String dishNames = menus.stream().map(Dish::getName).collect(Collectors.joining(","));
        System.out.println(dishNames);

        Integer collect3 = menus.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));
        System.out.println(collect3);
        Optional<Dish> collect4 = menus.stream().collect(Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
        System.out.println(collect4);
    }
}
