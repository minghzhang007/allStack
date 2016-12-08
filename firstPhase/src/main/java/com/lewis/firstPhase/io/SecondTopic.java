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
        /*int i = 10240;
        byte[] bytes = BinaryUtil.int2ByteArray(i);
        System.out.println(Arrays.toString(bytes));
        int i1 = BinaryUtil.byteArray2Int(bytes);
        BinaryUtil.printByteEleAsBinary(bytes);
        System.out.println(BinaryUtil.getFormatBinaryString(i));
        byte[] reverseBytes = BinaryUtil.reverseBytes(bytes);
        System.out.println(Arrays.toString(reverseBytes));
        BinaryUtil.printByteEleAsBinary(reverseBytes);
        int x = BinaryUtil.reverseInt(i);
        System.out.println(x);
        System.out.println(BinaryUtil.getFormatBinaryString(x));*/
        int i = 10240;
        bigEndian(i);
        littleEndian(i);
    }

    public static void bigEndian(int i) {
        File file = new File("D:\\bigEndin.txt");
        byte[] bytes = BinaryUtil.int2ByteArray(i);
        /*byte[] newBytes = new byte[4];
        for (int j = 0; j < bytes.length; j++) {
            newBytes[j] = bytes[bytes.length - 1 - j];
        }*/
        printFile(file, bytes);
        readFile(file);
    }

    public static void littleEndian(int i) {
        File file = new File("D:\\littleEndian.txt");
        byte[] bytes = BinaryUtil.int2ByteArray(i);
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
                System.out.println("LittleEndian direct read int :" + BinaryUtil.byteArray2Int(byte1));
                System.out.println("LittleEndian reverse read int :" + BinaryUtil.byteArray2Int(BinaryUtil.reverseBytes(byte1)));
            }else{
                byte[] bytes = new byte[4];
                fis.read(bytes);
                System.out.println("read bytes2Int:" + BinaryUtil.byteArray2Int(bytes));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void printFile(File file, byte[] bytes) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, false);
            fos.write(bytes);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
