package com.lewis.firstPhase.stream;

import com.lewis.jdk8.vo.Dish;
import com.lewis.jdk8.vo.VirtualDB;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhangminghua on 2017/1/3.
 */
public class StringCollectorTest {
    public static void main(String[] args) {
        List<Dish> dishList = VirtualDB.getMenus();
        Map<Dish.Type, List<Dish>> collect = dishList.stream().collect(Collectors.groupingBy(Dish::getType));
        System.out.println(collect.toString());
        String string = dishList.stream().map(Dish::getName).reduce(new StringCombiner(",", "[", "]"), StringCombiner::add, StringCombiner::merge).toString();
        System.out.println(string);

        String collect1 = dishList.stream().map(Dish::getName).collect(new StringCollector("|", "(", ")"));
        System.out.println(collect1);
    }
}
