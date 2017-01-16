package com.lewis.spring.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangminghua on 2017/1/5.
 */
public class OutOfMemeryErrorDemo {
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true) {
            list.add(new OOMObject());
            System.out.println(list.size());
        }
    }

    static class OOMObject{}
}
