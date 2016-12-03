package com.lewis.firstPhase.baseDataStructure;

import com.lewis.firstPhase.RandomUtil;
import com.lewis.firstPhase.Salary;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 * 定义Java类Salary {String name, int baseSalary, int bonus  },随机产生1万个实例，属性也随机产生（baseSalary范围是5-100万，
 * bonus为（0-10万），其中name长度为5，随机字符串，然后进行排序，排序方式为收入总和（baseSalary*13+bonus），
 * 输出收入最高的10个人的名单
 */
public class ForthTopic {
    public static void main(String[] args) {
        List<Salary> salaries = generateSalarys(10000);
        salaries.stream().sorted(Comparator.comparing((Salary s)-> s.getBaseSalary()*13+s.getBonus()).reversed())
                .limit(10).forEach(System.out::println);
    }

    public static List<Salary> generateSalarys(int count) {
        List<Salary> retList = new LinkedList<Salary>();
        for (int i = 0; i <= count; i++) {
            Salary salary = new Salary();
            salary.setBaseSalary(RandomUtil.getRandomInt(5, 1000000));
            salary.setBonus(RandomUtil.getRandomInt(0,100000));
            salary.setName(RandomUtil.getRandomString(5));
            retList.add(salary);
        }

         /* Comparator<Salary> comparator = Comparator.comparing((Salary salary) -> salary.getBaseSalary() * 13 + salary.getBonus()).reversed();
        List<Salary> list = salaries.stream().sorted(comparator)
                .limit(10).collect(Collectors.toList());
        salaries.forEach(System.out::println);
        System.out.println("===========");
        System.out.println("===========");
        list.stream().forEach(System.out::println);*/
        return retList;
    }



}



