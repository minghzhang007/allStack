package com.lewis.firstPhase.io;

import com.lewis.sort.BinaryUtil;

import java.io.*;

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
    public static void bigEndian(int i) {
        File file = new File("D:\\bigEndin.txt");
        byte[] bytes = BinaryUtil.int2ByteArray(i);
        printFile(file, bytes);
        readFile(file);
    }
    public static void littleEndian(int i) {
        File file = new File("D:\\littleEndian.txt");
        byte[] bytes = BinaryUtil.int2ByteArray(i);
        //将字节序列转为little endian的方式存储
        byte[] reverseBytes = BinaryUtil.reverseBytes(bytes);
        printFile(file, reverseBytes);
        readFile(file);
    }
    public static void readFile(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            if (file.getName().equals("littleEndian.txt")) {
                byte[] byte1 = new byte[4];
                fis.read(byte1);
                System.out.println("LittleEndian direct read int :"
                        + BinaryUtil.byteArray2Int(byte1));
                System.out.println("LittleEndian reverse read int :"
                        + BinaryUtil.byteArray2Int(BinaryUtil.reverseBytes(byte1)));
            }else{
                byte[] bytes = new byte[4];
                fis.read(bytes);
                System.out.println("read bytes2Int:" + BinaryUtil.byteArray2Int(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fis);
        }
    }
    public static void printFile(File file, byte[] bytes) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, false);
            fos.write(bytes);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(fos);
        }
    }
    private static void close(FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void close(FileOutputStream fos) {
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
