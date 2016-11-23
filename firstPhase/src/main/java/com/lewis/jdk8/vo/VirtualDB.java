package com.lewis.jdk8.vo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/11/21.
 */
public class VirtualDB {

    public static List<Dish> getMenus(){
        return Arrays.asList(
                new Dish("pork",false,800,Dish.Type.MEAT),
                new Dish("beef",false,700,Dish.Type.MEAT),
                new Dish("chicken",false,400,Dish.Type.MEAT),
                new Dish("french fries",false,530,Dish.Type.OTHER),
                new Dish("rice",true,350,Dish.Type.OTHER),
                new Dish("season fruits",true,120,Dish.Type.OTHER),
                new Dish("pizza",true,350,Dish.Type.OTHER),
                new Dish("prawn",false,300,Dish.Type.FISH),
                new Dish("salmon",false,450,Dish.Type.FISH)
        );
    }

    public static List<Transaction> getTransactions(){
        Trader raoul = new Trader("raoul","Cambridge");
        Trader mario = new Trader("mario","Milan");
        Trader alan = new Trader("alan","Cambridge");
        Trader brian = new Trader("brian","Cambridge");
        return Arrays.asList(
                new Transaction(brian,2011,300),
                new Transaction(raoul,2012,1000),
                new Transaction(raoul,2011,400),
                new Transaction(mario,2012,710),
                new Transaction(mario,2011,700),
                new Transaction(alan,2012,950));
    }
}
