package com.lewis.jdk8.vo;

import java.util.Optional;

/**
 * Created by zhangminghua on 2016/11/26.
 */
public class Person {
    private String name;

    private Optional<Car> car;

    public Person() {
    }

    public Person(String name, Optional<Car> car) {
        this.name = name;
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<Car> getCar() {
        return car;
    }

    public void setCar(Optional<Car> car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", car=" + car +
                '}';
    }
}
