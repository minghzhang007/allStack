package com.lewis.firstPhase.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Created by zhangminghua on 2016/12/12.
 */
public class FileChannelDemo {
    public static void main(String[] args) {
        String fileName ="D:\\allStack\\advanceSalarys.txt";
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName,"r");
            raf.seek(1000);
            FileChannel channel = raf.getChannel();
            System.out.println("file pos:"+channel.position());
            raf.seek(500);
            System.out.println("file pos:"+channel.position());
            channel.position(200);
            System.out.println("file pos:"+raf.getFilePointer());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
