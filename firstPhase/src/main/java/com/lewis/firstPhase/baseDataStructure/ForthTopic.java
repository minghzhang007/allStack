package com.lewis.firstPhase.baseDataStructure;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/11/19.
 * 定义Java类Salary {String name, int baseSalary, int bonus  },随机产生1万个实例，属性也随机产生（baseSalary范围是5-100万，
 * bonus为（0-10万），其中name长度为5，随机字符串，然后进行排序，排序方式为收入总和（baseSalary*13+bonus），
 * 输出收入最高的10个人的名单
 */
public class ForthTopic {
    public static void main(String[] args) {
        List<Salary> salaries = generateSalarys(10000);
        Comparator<Salary> comparator = Comparator.comparing((Salary salary) -> salary.getBaseSalary() * 13 + salary.getBonus()).reversed();
        List<Salary> list = salaries.stream().sorted(comparator)
                .limit(10).collect(Collectors.toList());
        salaries.forEach(System.out::println);
        System.out.println("===========");
        System.out.println("===========");
        list.stream().forEach(System.out::println);
    }

    public static List<Salary> generateSalarys(int count) {
        List<Salary> retList = new LinkedList<Salary>();
        for (int i = 0; i <= count; i++) {
            Salary salary = new Salary();
            salary.setBaseSalary(getRandomInt(100, 5));
            salary.setBonus(getRandomInt(10, 0));
            salary.setName(getRandomString(5));
            retList.add(salary);
        }
        return retList;
    }

    public static int getRandomInt(int max, int min) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);// [0,62)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}

class Salary {
    private String name;

    private int baseSalary;

    private int bonus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "name='" + name + '\'' +
                ", baseSalary=" + baseSalary +
                ", bonus=" + bonus +
                '}';
    }
}

