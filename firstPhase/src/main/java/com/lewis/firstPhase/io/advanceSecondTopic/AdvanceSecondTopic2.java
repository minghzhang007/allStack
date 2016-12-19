package com.lewis.firstPhase.io.advanceSecondTopic;

import com.lewis.firstPhase.RandomUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by zhangminghua on 2016/12/7.
 */
public class AdvanceSecondTopic2 {

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
                if (i < 100) {
                    System.out.println("i = " + i + " " + name + ",baseSalary:" + baseSalary + ", bonus:" + bonus+" "+ Arrays.toString(bytes));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
           /* while (byteBuffer.position() < byteBuffer.limit()) {
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
            }*/
            /*List<Record> list = new ArrayList<>(4096);
            for (int i = 0; i < totalSalaryArray.length; i++) {
                list.add(new Record(totalSalaryArray[i],count[i],new String(namePreArray[i])));
            }
            Collections.sort(list);
            list.stream().forEach(record ->  System.out.printf("%s,%d万,%d个\n", record.getNamePre(), record.getTotalSalary()/10000, record.getCount()));*/
            PriorityQueue<Record> queue = new PriorityQueue<>(10);
            for (int i = 0; i < totalSalaryArray.length; i++) {
                queue.add(new Record(totalSalaryArray[i],count[i],new String(namePreArray[i])));
            }

            for (int i = 0; i < 4096; i++) {
                Record record = queue.poll();
                System.out.printf("%s,%d万,%d个\n", record.getNamePre(), record.getTotalSalary()/10000, record.getCount());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int index(Object obj) {
        int hash = hash(obj);
        return hash & (totalSalaryArray.length - 1);
    }

    public static int hash(Object obj) {
        int h = obj.hashCode();
        return h ^ (h >>> 16);
    }

}
