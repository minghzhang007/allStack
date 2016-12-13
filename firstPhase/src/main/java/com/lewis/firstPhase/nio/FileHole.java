package com.lewis.firstPhase.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by zhangminghua on 2016/12/12.
 */
public class FileHole {
    public static void main(String[] args) {
        try {
            File tempFile = File.createTempFile("holy", null);
            RandomAccessFile raf = new RandomAccessFile(tempFile,"rw");
            FileChannel channel = raf.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(100);
            putData(0,buffer,channel);
            putData(5000000,buffer,channel);
            putData(50000,buffer,channel);
            System.out.println("Write tmpFile "+tempFile.getPath()+" ,size ="+channel.size());
            channel.close();
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void putData(int position, ByteBuffer buffer, FileChannel channel) throws IOException {
        String string ="*<-- location "+position;
        buffer.clear();
        buffer.put(string.getBytes("US-ASCII"));
        buffer.flip();
        channel.position(position);
        channel.write(buffer);
    }
}
