package com.lewis.jdk8.vo;

/**
 * Created by zhangminghua on 2016/11/26.
 */
public class Insurance {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Insurance(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "name='" + name + '\'' +
                '}';
    }
}
