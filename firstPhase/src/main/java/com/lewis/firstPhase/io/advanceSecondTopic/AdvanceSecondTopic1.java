package com.lewis.firstPhase.io.advanceSecondTopic;

import com.lewis.firstPhase.RandomUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.PriorityQueue;

/**
 * Created by zhangminghua on 2016/12/7.
 */
public class AdvanceSecondTopic1 {

    public static void main(String[] args) {
        long bT = System.currentTimeMillis();
         //generateFile("D:\\allStack\\1.txt",10000000);
        group("D:\\allStack\\1.txt");
        System.out.println("costTime:" + (System.currentTimeMillis() - bT));
    }

    public static void generateFile(String fileName, int count) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
            FileChannel fc = raf.getChannel();
            //每条记录 5个byte的name,一个int的baseSalary,一个int的bonus,一共5+4+4=13个字节
            int fileSize = count * 13;
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
            for (int i = 0; i < count; i++) {
                int baseSalary = RandomUtil.getRandomInt(5, 1000000);
                int bonus = RandomUtil.getRandomInt(0, 100000);
                String name = RandomUtil.getRandomString(5);
                byte[] bytes = name.getBytes("utf-8");
                byteBuffer.put(bytes);
                byteBuffer.putInt(baseSalary);
                byteBuffer.putInt(bonus);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    //62*62 =3844 < 2^12=4096
    private static long[] totalSalaryArray = new long[4096];

    private static int[] count = new int[4096];

    private static byte[][] namePreArray = new byte[4096][2];

    public static void group(String fileName) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "r");
            FileChannel channel = raf.getChannel();
            MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            while (byteBuffer.position() < byteBuffer.limit()) {
                byte[] nameBytes = new byte[5];
                byteBuffer.get(nameBytes);
                int baseSalary = byteBuffer.getInt();
                int bonus = byteBuffer.getInt();
                int totalSalary = baseSalary * 13 + bonus;
                byte[] namePreBytes = new byte[2];
                namePreBytes[0] = nameBytes[0];
                namePreBytes[1] = nameBytes[1];
                int index = index(namePreBytes);
                totalSalaryArray[index] += totalSalary;
                count[index] += 1;
                namePreArray[index] = namePreBytes;
            }
            PriorityQueue<ByteBuffer> queue = new PriorityQueue<>(10,
                    (o1, o2) -> {
                        return Long.compare(o2.getLong(0), o1.getLong(0));
                    }
            );
            for (int i = 0; i < totalSalaryArray.length; i++) {
                //一个long类型的8个byte,一个int类型的4个字节，2个byte的name,一共14个字节
                ByteBuffer buffer = ByteBuffer.allocate(14);
                buffer.putLong(totalSalaryArray[i]);
                buffer.putInt(count[i]);
                buffer.put(namePreArray[i]);
                buffer.flip();
                queue.add(buffer);
            }
            StringBuilder sb = new StringBuilder();
            int total= 0;
            for (int i = 0; i < 4096; i++) {
                ByteBuffer buffer = queue.poll();
                long tatalSalary = buffer.getLong();
                int count = buffer.getInt();
                byte[] name = new byte[2];
                buffer.get(name);
                sb.append(new String(name, Charset.forName("utf-8"))).append(",")
                        .append(tatalSalary/10000).append("万,")
                        .append(count).append("个人");
                System.out.println(sb.toString());
                sb.delete(0, sb.toString().length());
                total +=count;
            }
            System.out.println("totalCount:"+total);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //求name在数组中的下标
    public static int index(Object obj) {
        int hash = hash(obj);
        return hash & (totalSalaryArray.length - 1);
    }
    //仿照HashMap中hash的实现
    public static int hash(Object obj) {
        int h = obj.hashCode();
        return h ^ (h >>> 16);
    }

}
