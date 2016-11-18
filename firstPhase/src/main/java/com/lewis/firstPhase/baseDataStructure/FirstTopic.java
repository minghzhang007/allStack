package com.lewis.firstPhase.baseDataStructure;

/**
 * Created by Administrator on 2016/11/18.
 * 1题
 byte ba=127;
 byte bb=ba<<2;
 System.out.println(bb);
 这个为什么会出错？给出解释，并且纠正错误

 位运算是针对整形的，进行位运算时，除了long型外，其余类型都会转化为int类型
 上面byte ba = 127;
 ba <<2 ba左移运算的过程中,变量ba已经扩展为了int类型，那把范围大的int类型赋值给范围小的byte类型，
 肯定是不允许的,因为有很大可能会溢出
 如果移动的位数超过了32位(long是64位），那么编译器
 会对移动的位数取模。如对int型移动33位，实际上只移
 动了33%32=1位，64位的long型也是同理的。
 */
public class FirstTopic {
    public static void main(String[] args) {
        byte ba = 1;
       // byte bb = ba << 2; compiler error
       // System.out.println(bb);
    }
}
