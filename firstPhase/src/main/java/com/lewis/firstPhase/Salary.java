package com.lewis.firstPhase;

/**
 * Created by zhangminghua on 2016/11/20.
 */
public class Salary {

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

    public Salary(String name, int baseSalary, int bonus) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

    public Salary() {
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
