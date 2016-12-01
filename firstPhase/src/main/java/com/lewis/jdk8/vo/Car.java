package com.lewis.jdk8.vo;

import java.util.Optional;

/**
 * Created by zhangminghua on 2016/11/26.
 */
public class Car {

    private String name;
    private Optional<Insurance> insurance;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<Insurance> getInsurance() {
        return insurance;
    }

    public void setInsurance(Optional<Insurance> insurance) {
        this.insurance = insurance;
    }

    public Car(String name, Optional<Insurance> insurance) {
        this.name = name;
        this.insurance = insurance;
    }

    public Car() {
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", insurance=" + insurance +
                '}';
    }
}
