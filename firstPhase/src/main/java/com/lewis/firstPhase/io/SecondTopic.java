package com.lewis.firstPhase.io;

import com.lewis.sort.BinaryUtil;

import java.io.*;
import java.util.Arrays;

/**
 * Created by zhangminghua on 2016/12/6.
 * 2 分别用大头和小头模式将整数 a=10240写入到文件中（4个字节），并且再正确读出来，打印到屏幕上，
 * 同时截图UltraEdit里的二进制字节序列，做对比说明
 */
public class SecondTopic {

    public static void main(String[] args) {
        int i = 10240;
        int j =2621440;
        System.out.println(getIntUsingBigEndian(BinaryUtil.int2ByteArray(j),0));
      /*  bigEndian(i);
        littleEndian(i);*/
    }

    public static void bigEndian(int i){
        File file = new File("D:\\bigEndin.txt");
        byte[] bytes = BinaryUtil.int2ByteArray(i);
        System.out.println("bigEndian bytes"+Arrays.toString(bytes));
        printFile(file,bytes,i);
        readFile(file);
    }

    public static void littleEndian(int i){
        File file = new File("D:\\littleEndian.txt");
        byte[] bytes = BinaryUtil.int2ByteArray(i);
        System.out.println(Arrays.toString(bytes));
        printFile(file,bytes,i);
        readFile(file);
    }

    public static void readFile(File file){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            //int readInt = fis.read();
            byte[] bytes = new byte[4];
            fis.read(bytes);
            //System.out.println("readInt from file :"+ readInt);
            System.out.println("readBytes:"+Arrays.toString(bytes));
            System.out.println("read bytes2Int:"+BinaryUtil.byteArray2Int(bytes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void printFile(File file,byte[] bytes,int i){
        FileOutputStream fos = null;
        try {
            //int i = BinaryUtil.byteArray2Int(bytes);
            fos = new FileOutputStream(file,false);
            fos.write(bytes);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getIntUsingBigEndian(byte[] bytes,int off){
        int vo = 0;
        int v0 = bytes[off] >> 24;
        int v1 = (bytes[off + 1] >> 16) & 0xff;
        int v2 = (bytes[off + 2] >> 8) & 0xff;
        int v3 = (bytes[off + 3]) & 0xff;
        vo = v0+ v1 + v2+v3;
        return vo;
    }

    public static int getIntUsingLittleEndian(byte[] bytes,int off){
        return 0;
    }
}
