package com.lewis.firstPhase.baseDataStructure;

import com.lewis.firstPhase.Salary;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 * 定义Java类Salary {String name, int baseSalary, int bonus  },随机产生1万个实例，属性也随机产生（baseSalary范围是5-100万，
 * bonus为（0-10万），其中name长度为5，随机字符串，然后进行排序，排序方式为收入总和（baseSalary*13+bonus），
 * 输出收入最高的10个人的名单
 */
public class ForthTopic {
    public static void main(String[] args) {
        List<Salary> salaries = SalaryFactory.generateSalarys(10000);
        salaries.stream().sorted(Comparator.comparing((Salary s)-> s.getBaseSalary()*13+s.getBonus()).reversed())
                .limit(10).forEach(System.out::println);
    }
}



