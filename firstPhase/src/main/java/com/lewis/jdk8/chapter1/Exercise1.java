package com.lewis.jdk8.chapter1;

import com.lewis.jdk8.vo.Dish;
import com.lewis.jdk8.vo.VirtualDB;

import java.util.List;

/**
 * Created by zhangminghua on 2017/1/5.
 */
public class Exercise1 {
    public static void main(String[] args) {
        List<Dish> menus = VirtualDB.getMenus();
        long count = menus.stream().filter(x -> {
            System.out.println(x);
            return x.getType() == Dish.Type.MEAT;
        }).count();
        System.out.println(count);
    }
}
