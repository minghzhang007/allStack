package com.lewis.jdk8.vo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2016/11/21.
 * 1.找出2011年发生的所有交易，并按照交易额排序（从低到高）
 * 2.交易员都在哪些不同的城市工作过
 * 3.查找来自剑桥的交易员，并按照姓名排序
 * 4.返回所有交易员的姓名字符串，并按字母排序
 * 5.有没有交易员在米兰工作？
 * 6.打印生活在剑桥的交易员的所有交易额
 * 7.所有的交易中，最高的交易额是多少？
 * 8.找出交易额最小的交易
 */
public class Task {

    public static void main(String[] args) {
        List<Transaction> transactions = VirtualDB.getTransactions();
        List<Transaction> list = getAllTransactionsOcurrIn2011(transactions.stream());
        list.forEach(System.out::println);
        List<String> citys = getCitysOfAllTrader(transactions.stream());
        citys.forEach(System.out::println);
        System.out.println("===");
        getTradersOfCambridge(transactions.stream()).forEach(System.out::println);
    }

    //找出2011年发生的所有交易，并按照交易额排序（从低到高）
    public static List<Transaction> getAllTransactionsOcurrIn2011(Stream<Transaction> stream){
      return   stream.filter(t -> t.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
    }

    //交易员都在哪些不同的城市工作过
    public static List<String> getCitysOfAllTrader(Stream<Transaction> stream){
        return stream.map(t -> t.getTrader().getCity()).distinct().collect(Collectors.toList());
    }

    //查找来自剑桥的交易员，并按照姓名排序
    public static List<String> getTradersOfCambridge(Stream<Transaction> stream){
        return stream.map(Transaction::getTrader).filter(trader -> "Cambridge".equals(trader.getCity())).map(Trader::getName).distinct().sorted()
                .collect(Collectors.toList());
    }

}
