package com.lewis.firstPhase.baseDataStructure;

import java.util.Random;

/**
 * Created by Administrator on 2016/11/19.
 *
 3题
 定义一个10240*10240的byte数组，分别采用行优先与列优先的循环方式来计算 这些单元格的总和，看看性能的差距，并解释原因
 行优先的做法，每次遍历一行，然后到下一行。

 多维数组：就是数组的元素依然是数组的数组
 有矩阵A :3行4列
 A00 A01 A02 A03
 A10 A11 A12 A13
 A20 A21 A22 A23
 用二维数组可以如下表示：假设都是int类型的
 int[][] A = new int[3][4];
 二维数组A中元素A[0]是个一维数组即：new int[4];内容为{A00 A01 A02 A03}
 二维数组A中元素A[1]是个一维数组即：new int[4];内容为{A10 A11 A12 A13}
 二维数组A中元素A[2]是个一维数组即：new int[4];内容为{A20 A21 A22 A23}
 数组的元素是内存地址连续的，A的内存中的逻辑排练为：A[0],A[1],A[2]
 再具体点就是：[A00 A01 A02 A03],[A10 A11 A12 A13],[A20 A21 A22 A23]
 可以看出二维数组是按照行优先来存储的，其元素的内存地址是连续的
 这和数组在内存中的存储顺序有关，数组是行优先存储，也就是每行的数据都是连续的，而每列的数据是不连续的，所以按行访问更快。
 */
public class ThirdTopic{

    public static void main(String[] args) {
        byte[][] array = getBytesArray();
        System.out.println(array.getClass().getName());
        long beginTime= System.currentTimeMillis();
        long sum = accessRowPrior(array);
        System.out.println("accessRowPrior  sum is "+sum +" costTime is "+(System.currentTimeMillis()-beginTime));
        beginTime= System.currentTimeMillis();
        sum = accessColumnPrior(array);
        System.out.println("accessColumnPrior sum is "+sum +" costTime is "+(System.currentTimeMillis()-beginTime));
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    public static long accessRowPrior(byte[][] array){
        long sum = 0;
        for (int i = 0; i < array.length; i++) {
            int subArrayLength = array[i].length;
            for (int j = 0; j < subArrayLength; j++) {
                sum += array[i][j];
            }
        }
        return sum;
    }

    public static long accessColumnPrior(byte[][] array){
        long sum = 0;
        for (int i = 0; i < array.length; i++) {
            int subArrayLength = array[i].length;
            for (int j = 0; j < subArrayLength; j++) {
                sum += array[j][i];
            }
        }
        return sum;
    }

    private static byte[][] getBytesArray() {
        Random r = new Random();
        byte[][] array = new byte[10240][10240];
        int length = array.length;
        for (int i = 0; i < length; i++) {
            int subArrayLength =  array[i].length;
            for (int j = 0; j < subArrayLength; j++) {
                array[i][j]=(byte)r.nextInt(128);
            }
        }
        return array;
    }

}
