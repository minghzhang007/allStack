package com.lewis.firstPhase.baseDataStructure;

import com.lewis.firstPhase.RandomUtil;
import com.lewis.firstPhase.Salary;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangminghua on 2016/12/3.
 */
public class SalaryFactory {
    public static List<Salary> generateSalarys(int salaryCount) {
        List<Salary> retList = new LinkedList<Salary>();
        for (int i = 0; i <= salaryCount; i++) {
            Salary salary = new Salary();
            salary.setBaseSalary(RandomUtil.getRandomInt(5, 1000000));
            salary.setBonus(RandomUtil.getRandomInt(0, 100000));
            salary.setName(RandomUtil.getRandomString(5));
            retList.add(salary);
        }
        return retList;
    }
}
