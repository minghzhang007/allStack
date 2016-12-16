package com.lewis.firstPhase.baseDataStructure;

import com.lewis.firstPhase.Salary;
import com.lewis.sort.SwapUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangminghua on 2016/12/3.
 * 7题
 * DualPivotQuicksort算法与普通冒泡算法相比，有哪些改进，对比常见的几种基于数组的排序算法，说说为什么Java选择了快排
 * 以下是加分题目
 * 第一：
 * 写出标准冒泡排序与快速排序的算法，排序对象为上面说的 Salary {name, baseSalary, bonus  },收入总和为baseSalary*13+bonus,以收入总和为排序标准。
 * 排序输出 年薪最高的100个人，输出结果为 xxxx:yyyy万
 * 第二：
 * 第五题中的 storeByteArry改为int[]数组，采用Java位操作方式来实现1个Int 拆解为4个Byte，存放MyItem对象的属性。
 */
public class SeventhTopic {

    public static void main(String[] args) {
        List<Salary> salaryList = SalaryFactory.generateSalarys(10000);
        Salary[] salaries = new Salary[salaryList.size()];
        salaryList.toArray(salaries);
        Salary[] newSalarys = Arrays.copyOf(salaries, salaries.length);
        bubbleSortSalary(salaries);
        Arrays.asList(salaries).stream().limit(100).forEach(s -> {
            System.out.println(s.getName()+":"+getTotalSalary(s)/10000+"万");
        });
        System.out.println("+++++++++");
        quickSort(newSalarys);
        Arrays.asList(newSalarys).stream().limit(100).forEach(s ->{
            System.out.println(s.getName()+":"+getTotalSalary(s)/10000+"万");
        });
    }

    public static void bubbleSortSalary(Salary[] salaries) {
        int length = salaries.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (lessThen(salaries[i], salaries[j])) {
                    SwapUtil.swap(salaries, i, j);
                }
            }
        }
    }

    private static boolean lessThen(Salary s1, Salary s2) {
        return getTotalSalary(s1) - getTotalSalary(s2) < 0;
    }

    public static int getTotalSalary(Salary salary) {
        return salary.getBaseSalary() * 13 + salary.getBonus();
    }

    public static void quickSort(Salary[] salaries) {
        quickSort(salaries, 0, salaries.length - 1);
    }

    public static void quickSort(Salary[] salaries,int left,int right){
        if (left < right) {
            int centerIndex = partition(salaries, left, right);
            quickSort(salaries,left,centerIndex-1);
            quickSort(salaries,centerIndex+1,right);
        }
    }

    public static int partition(Salary[] salaries, int left, int right) {
        int i = left;
        int j = right;
        Salary salary = salaries[left];
        int totalSalaryX = getTotalSalary(salary);
        while (i < j) {
            //从右往左 找比totalSalaryX 大的
            while (i < j && getTotalSalary(salaries[j]) <= totalSalaryX) {
                j--;
            }
            if (i < j) {
                salaries[i] = salaries[j];
                i++;
            }
            //从左往右找 比totalSalaryX 小的
            while (i < j && getTotalSalary(salaries[i]) >= totalSalaryX) {
                i++;
            }
            if (i < j) {
                salaries[j] = salaries[i];
                j--;
            }
        }
        salaries[i] = salary;
        return i;
    }
}
