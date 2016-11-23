package com.lewis.jdk8.vo;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by zhangminghua on 2016/11/22.
 */
public class GouGuShu {
    public static void main(String[] args) {
        getGouGushu();
    }

    /**
     * 输出勾股数
     */
    public static void getGouGushu() {
        Stream<int[]> stream = IntStream.rangeClosed(1, 10000).boxed().flatMap(
                a -> IntStream.rangeClosed(a, 10000).filter(b -> Math.sqrt(a * a + b * b) % 1.0 == 0).boxed().map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
        );
        stream.limit(50).forEach(t -> System.out.println(t[0] + "," + t[1] + "," + t[2]));
    }
}
