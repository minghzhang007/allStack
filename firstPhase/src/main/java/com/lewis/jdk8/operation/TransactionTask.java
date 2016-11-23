package com.lewis.jdk8.operation;

import com.lewis.jdk8.vo.Trader;
import com.lewis.jdk8.vo.Transaction;
import com.lewis.jdk8.vo.VirtualDB;
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
public class TransactionTask {

    public static void main(String[] args) {
        List<Transaction> transactions = VirtualDB.getTransactions();
        List<Transaction> list = getAllTransactionsOcurrIn2011(transactions.stream());
        list.forEach(System.out::println);
        List<String> citys = getCitysOfAllTrader(transactions.stream());
        citys.forEach(System.out::println);
        System.out.println("===");
        getTradersOfCambridge(transactions.stream()).forEach(System.out::println);
        System.out.println("===");
        System.out.println(getTraderNames(transactions.stream()));
        System.out.println("===");
        System.out.println(isTraderWorkAtMilan(transactions.stream()));
        System.out.println(getAllValueOfCambridge(transactions.stream()));
        System.out.println("===");
        System.out.println(getMaxValue(transactions.stream()));
        System.out.println(getMinValueOfTransactions(transactions.stream()));
        List<Double> list1 = new ArrayList(){
            {add(1.0);add(2.0);add(3.0);add(3.4);add(4.0);
            }
        };
        list1.stream().filter(x -> x%1.0 ==0).forEach(System.out::println);
    }

    //1.找出2011年发生的所有交易，并按照交易额排序（从低到高）
    public static List<Transaction> getAllTransactionsOcurrIn2011(Stream<Transaction> stream){
      return   stream.filter(t -> t.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
    }

    //2.交易员都在哪些不同的城市工作过
    public static List<String> getCitysOfAllTrader(Stream<Transaction> stream){
        return stream.map(t -> t.getTrader().getCity()).distinct().collect(Collectors.toList());
    }

    //3.查找来自剑桥的交易员，并按照姓名排序
    public static List<Trader> getTradersOfCambridge(Stream<Transaction> stream){
      return  stream.filter(t -> "Cambridge".equals(t.getTrader().getCity())).map(Transaction::getTrader).distinct().sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
    }

    //4.返回所有交易员的姓名字符串，并按字母排序
    public static String getTraderNames(Stream<Transaction> stream){
       // stream.map(t -> t.getTrader().getName()).distinct().sorted().reduce("",(x1,x2)->x1+x2);
        return stream.map(t -> t.getTrader().getName()).distinct().sorted().collect(Collectors.joining(","));
    }

    //5.有没有交易员在米兰工作？
    public static boolean isTraderWorkAtMilan(Stream<Transaction> stream){
        //return stream.anyMatch(t->t.getTrader().getCity().equals("Milan"));
        return stream.map(t-> t.getTrader()).anyMatch(t->t.getCity().equals("Milan"));
    }

    //6.打印生活在剑桥的交易员的所有交易额
    public static int getAllValueOfCambridge(Stream<Transaction> stream){
        return stream.filter(t->"Cambridge".equals(t.getTrader().getCity())).mapToInt(t->t.getValue()).sum();
    }

    //7.所有的交易中，最高的交易额是多少？
    public static int getMaxValue(Stream<Transaction> stream){
        //return stream.map(Transaction::getValue).reduce(Integer::max);
        return stream.mapToInt(t->t.getValue()).max().getAsInt();
    }

    //8.找出交易额最小的交易
    public static Transaction getMinValueOfTransactions(Stream<Transaction> stream){
       // stream.min(Comparator.comparing(Transaction::getValue));
        return stream.sorted(Comparator.comparing(Transaction::getValue)).limit(1).collect(Collectors.toList()).get(0);
    }

}
