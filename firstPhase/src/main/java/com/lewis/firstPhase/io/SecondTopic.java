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
        bigEndian(i);
        littleEndian(i);
    }

    public static void bigEndian(int i){
        File file = new File("D:\\bigEndin.txt");
        byte[] bytes = BinaryUtil.int2ByteArray(i);
        byte[] newBytes = new byte[4];
        for (int j = 0; j < bytes.length; j++) {
            newBytes[j] = bytes[bytes.length-1-j];
        }
        System.out.println(Arrays.toString(newBytes));
        printFile(file,newBytes);
        readFile(file);
    }

    public static void littleEndian(int i){
        File file = new File("D:\\littleEndian.txt");
        byte[] bytes = BinaryUtil.int2ByteArray(i);
        System.out.println(Arrays.toString(bytes));
        printFile(file,bytes);
        readFile(file);
    }

    public static void readFile(File file){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            if (file.getName().equals("littleEndian.txt")) {
                byte[] byte1 = new byte[4];
                fis.read(byte1);
                /*int anInt = getInt(byte1, 0);
                System.out.println("anInt:"+anInt);*/
                byte[] bigEndian = new byte[4];
                for (int i = 0; i < byte1.length; i++) {
                    bigEndian[i]=byte1[byte1.length-1-i];
                }
                System.out.println(Arrays.toString(byte1));
                System.out.println(Arrays.toString(bigEndian));
                System.out.println("LittleEndian read int :"+BinaryUtil.byteArray2Int(byte1));
            }
            int readInt = fis.read();
            byte[] bytes = new byte[4];
            fis.read(bytes);
            System.out.println("readInt from file :"+ readInt);
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

    public static void printFile(File file,byte[] bytes){
        FileOutputStream fos = null;
        try {
            int i = BinaryUtil.byteArray2Int(bytes);
            System.out.println("i ="+i);
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

    static int getInt(byte[] b, int off) {
        return ((b[off + 3] & 0xFF)      ) +
                ((b[off + 2] & 0xFF) <<  8) +
                ((b[off + 1] & 0xFF) << 16) +
                ((b[off    ]       ) << 24);
    }
}
