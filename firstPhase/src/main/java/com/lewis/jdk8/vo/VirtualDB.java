package com.lewis.jdk8.vo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/11/21.
 */
public class VirtualDB {

    public static List<Transaction> getTransactions(){
        Trader raoul = new Trader("raoul","Cambridge");
        Trader mario = new Trader("mario","Milan");
        Trader alan = new Trader("alan","Cambridge");
        Trader brian = new Trader("brian","Cambridge");
        return Arrays.asList(new Transaction(brian,2011,300),new Transaction(raoul,2012,1000),
                new Transaction(raoul,2011,400),new Transaction(mario,2012,710),new Transaction(mario,2011,700),
                new Transaction(alan,2012,950));
    }
}
