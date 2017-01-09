package com.lewis.firstPhase.stream;

import com.lewis.firstPhase.RandomUtil;
import com.lewis.firstPhase.Salary;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by zhangminghua on 2016/12/29.
 * 4.自己动手编写不少于5个Stream的例子，并解释代码
 */
public class ForthTopic {
    public static void main(String[] args) {
        Stream<Salary> stream = getSalaryStream(10000);
        stream.filter(ananualSalaryGreater10WPredicate()).map(s -> s.getName() + "年薪：" + getAnnualSalary(s)).limit(100).forEach(System.out::println);
        stream = getSalaryStream(10000);

        System.out.println("++++++");
        System.out.println("++++++");
        System.out.println("++++++");
        List<Record> list = stream.filter(ananualSalaryGreater10WPredicate())
                .map(s -> {
                    return new Record(s.getName(), getAnnualSalary(s));
                })
                .sorted(Comparator.comparing(Record::getAnnualSalary).reversed())
                .limit(100).collect(Collectors.toList());
        list.stream().forEach(System.out::println);
        System.out.println("=======");
        System.out.println("=======");
        System.out.println("=======");
        stream = getSalaryStream(1000000);
        stream.filter(s-> {
            char[] chars = s.getName().toCharArray();
            return (chars[0]=='a'||chars[0]=='A') && (chars[1]=='b'||chars[1]=='B') && (chars[2]=='c' || chars[2]=='C');
        }).map(s ->{return new Record(s.getName(),getAnnualSalary(s));}).sorted(Comparator.comparing(Record::getAnnualSalary).reversed())
                .forEach(System.out::println);
    }

    private static Predicate<Salary> ananualSalaryGreater10WPredicate() {
        return s -> (getAnnualSalary(s)) > 100000;
    }

    private static int getAnnualSalary(Salary s) {
        return s.getBaseSalary() * 13 + s.getBonus();
    }

    public static Stream<Salary> getSalaryStream(int count) {
        List<Salary> salaryList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Salary salary = new Salary(RandomUtil.getRandomString(5), RandomUtil.getRandomInt(5000, 20000), RandomUtil.getRandomInt(10000, 30000));
            salaryList.add(salary);
        }
        return salaryList.stream();
    }

    static class Record {
        private String name;

        private int annualSalary;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAnnualSalary() {
            return annualSalary;
        }

        public void setAnnualSalary(int annualSalary) {
            this.annualSalary = annualSalary;
        }

        public Record(String name, int annualSalary) {
            this.name = name;
            this.annualSalary = annualSalary;
        }

        public Record() {
        }

        @Override
        public String toString() {
            return "Record{" +
                    "name='" + name + '\'' +
                    ", annualSalary=" + annualSalary +
                    '}';
        }
    }
}


