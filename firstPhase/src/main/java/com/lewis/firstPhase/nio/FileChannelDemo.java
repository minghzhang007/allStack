package com.lewis.firstPhase.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * Created by zhangminghua on 2016/12/12.
 */
public class FileChannelDemo {
    public static void main(String[] args) {
        String fileName ="D:\\allStack\\advanceSalarys.txt";
        try {
            RandomAccessFile raf = new RandomAccessFile("","r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
