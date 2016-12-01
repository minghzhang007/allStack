package com.lewis.firstPhase.baseDataStructure;

/**
 * Created by Administrator on 2016/11/18.
 */
public class HexByte {
    public static void main(String[] args) {
        int x=0xf0;
        byte b = (byte)0xf0;
        System.out.println(x);
        System.out.println(b & 0xff);
        int flag = 0B01101000;
        int i = (flag & 0B00110000) >> 4;
        System.out.println(flag);
        System.out.println(i);
        String name="lewis";
        System.out.println();
        System.out.println(1<<13);
    }

    public static void test(){
        char[] hex = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        byte b = (byte)0xf1;
        System.out.println("b=0x"+hex[(b >> 4) & 0x0f] + hex[b & 0x0f]);
        //b == 240
        //b 1111 0001 向左移动4位就是0000  1111
        //0x0f                      0000  1111  进行&运算还是 0000 1111 即15

        //b    1111 0001
        //0x0f 0000 1111  进行& 运算为 0000 0001 即1
    }

    public static int getUnsignedByte(byte data){
        return data & 0xff;
    }

    public static int getUnsignedShort(short data){
        return data & 0xffff;
    }

    public static int getUnsignedInt(int data){
        return data & 0xffffffff;
    }
}
