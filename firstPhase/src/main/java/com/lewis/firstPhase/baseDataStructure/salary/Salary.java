package com.lewis.firstPhase.baseDataStructure.salary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by Administrator on 2016/12/12.
 */
public class Salary {
    private static byte[] name = new byte[5];
    private static Random rand = new Random(System.currentTimeMillis());
    private static final int dictSize = 26 * 26;
    private static final int alphaSize = 26;
    private static void randName(Random rand) {
        for (int i = 0; i < 5; i++) {
            name[i] = (byte)(rand.nextInt(alphaSize) + 97);
        }
    }
    private static void gen(String fileName, int number) {
        try {
            RandomAccessFile file = new RandomAccessFile(fileName, "rw");
            long fileSize = number * 7;
            file.seek(fileSize);
            FileChannel fch = file.getChannel();
            MappedByteBuffer buffer  = fch.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
            for (int i = 0; i < number; i++) {
                int salary = rand.nextInt(10) + 1;
                int bonus  = rand.nextInt(6);
                randName(rand);
                buffer.put(name);
                buffer.put((byte) salary);
                buffer.put((byte) bonus);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] prefix = new byte[5];
    private static short encode() {
        byte high = (byte) (prefix[1] - 97);
        byte low  = (byte) (prefix[0] - 97);
        return (short) ((high << 4) + (high << 3) + (high << 1)  + low);
    }
    private static byte[] decode(int index) {
        byte[] ret = new byte[2];
        ret[0] = (byte) (index % 26 + 97);
        ret[1] = (byte) (index / 26 + 97);
        return ret;
    }

    private static int[] total = new int[dictSize];
    private static int[] count = new int[dictSize];
    private static void group(String fileName) {
        try {
            RandomAccessFile file = new RandomAccessFile(fileName, "r");
            FileChannel fch = file.getChannel();
            MappedByteBuffer buffer = fch.map(FileChannel.MapMode.READ_ONLY,0, file.length());
            while (buffer.position() < buffer.limit()) {
                buffer.get(prefix);
                byte salary = buffer.get();
                byte bonus = buffer.get();
                short index = encode();
                int yearSalary = ((salary << 3) + (salary << 2) + salary + bonus);
                total[index] += yearSalary;
                count[index] += 1;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PriorityQueue<ByteBuffer> queue = new PriorityQueue<>(10,
                (o1, o2) -> Integer.compare(o2.getInt(0), o1.getInt(0)));

        for(int i = 0; i < dictSize; i++) {
            ByteBuffer buffer = ByteBuffer.allocate(10);
            buffer.putInt(total[i]);
            buffer.putInt(count[i]);
            buffer.put(decode(i));
            buffer.position(0);
            queue.add(buffer);
        }
        for(int i = 0; i < 10; i++) {
            ByteBuffer buffer  = queue.poll();
            byte[] prefix = new byte[2];
            int total = buffer.getInt();
            int count = buffer.getInt();
            buffer.get(prefix);

            StringBuilder bb = new StringBuilder();
            bb.append(new String(prefix));
            bb.append(',');
            bb.append(total);
            bb.append(',');
            bb.append(count);
            System.out.println(bb.toString());
        }
    }

    public static void main(String[] args) {
        long t0 = System.currentTimeMillis();
        gen("byteSalary.txt", 1000_0000);
        group("byteSalary.txt");
        System.out.println(System.currentTimeMillis() - t0);
    }
}
