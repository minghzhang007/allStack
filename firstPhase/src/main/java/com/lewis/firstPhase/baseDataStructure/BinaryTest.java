package com.lewis.firstPhase.baseDataStructure;

import com.lewis.sort.BinaryUtil;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/12/3.
 */
public class BinaryTest {

    public static void main(String[] args) {
        //justTest();
        test();
    }


    public static void test(){
        int i=2000000000;
        String formatBinaryString = BinaryUtil.getFormatBinaryString(i);
        System.out.println(formatBinaryString);
        //0111 0111 0011 0101 1001 0100 0000 0000
        int j = (i >> 24) & 0xff;
        System.out.println(BinaryUtil.getFormatBinaryString(j));
        System.out.println(BinaryUtil.getFormatBinaryString(i >> 24));
        int k = (i >> 16) &0xff;
        System.out.println(BinaryUtil.getFormatBinaryString(k));
        System.out.println(BinaryUtil.getFormatBinaryString(i >> 16));
    }

    public static void justTest(){
        int number = 2000000000;
        String binaryString = Integer.toBinaryString(number);
        System.out.println(binaryString);
        String s = BinaryUtil.getFormatBinaryString(binaryString);
        System.out.println(s);

        byte i = (byte) ((number >> 24) & 0xff);
        System.out.println(BinaryUtil.getFormatBinaryString(Integer.toBinaryString(i)));

        byte j = (byte)((number >> 16) & 0xff);
        System.out.println(BinaryUtil.getFormatBinaryString(Integer.toBinaryString(j)));

        byte l = (byte)((number >> 8) & 0xff);
        System.out.println(BinaryUtil.getFormatBinaryString(Integer.toBinaryString(l)));

        byte k = (byte)((number) & 0xff);
        System.out.println(BinaryUtil.getFormatBinaryString(Integer.toBinaryString(k)));
        byte[] a = int2Bytes(number);
        System.out.println(Arrays.toString(a));
        System.out.println();
        System.out.println();
        for (byte b : a) {
            System.out.println(BinaryUtil.getFormatBinaryString(Integer.toBinaryString(b)));
        }

        System.out.println(BinaryUtil.byteArray2Int(a));

    }


    //0011 1011  1001 1010  1100 1010  0000 0000
    //0011 1011  1001 1010  1100 1010  0000 0000

    //[0000 0101]  [1111 0101]  [1110 0001]  [0000 0000]
    //  a[0]          a[1]          a[2]          a[3]
    //0000 0000    0000 0000    0000 0000    0000 0000

    public static int byteArray2Int(byte[] bytes){
        int v0 = (bytes[0] & 0xff)<<24;
        int v1 = (bytes[1] & 0xff)<<16;
        int v2 = (bytes[2] & 0xff)<<8;
        int v3 = (bytes[3] & 0xff);
        System.out.println("result is "+(v0+v1+v2+v3));
        return v0|v1|v2|v3;
    }

    public static byte[] int2Bytes(int i){
        byte[] bytes = new byte[4];
        for (int j = 0; j < 4; j++) {
            bytes[j]= (byte) (i >>> (24-8*j));
        }
        return bytes;
    }

    public static byte[] int2Bytes1(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (i >>24);
        bytes[1] = (byte) (i >>16);
        bytes[2] = (byte) (i >>8);
        bytes[3] = (byte) (i );
        return bytes;
    }

}
