package com.lewis.firstPhase.multiThreadUp;

import com.lewis.firstPhase.RandomUtil;
import com.lewis.firstPhase.io.advanceSecondTopic.StartEndIndexPair;

import java.io.*;
import java.util.List;

/**
 * Created by zhangminghua on 2017/1/17.
 * 多线程版：随机生成 Salary {name, baseSalary, bonus  }的记录，如“wxxx,10,1”，每行一条记录，总共1000万记录，写入文本文件（UFT-8编码），
 * 然后读取文件，name的前两个字符相同的，其年薪累加，比如wx，100万，3个人，最后做排序和分组，输出年薪总额最高的10组：
 * wx, 200万，10人
 * lt, 180万，8人
 */
public class AdvancedTopic {

    private static final String charset = "utf-8";

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        long start = System.currentTimeMillis();
        String path = "D:\\allStack\\2.txt";
        File file = new File(path);
        RandomAccessFile raf;
        raf = new RandomAccessFile(file, "rw");
        writeFile(40, raf);
        System.out.println("costTime:" + (System.currentTimeMillis() - start));


    }

    public static void readFile(RandomAccessFile raf) {
        List<StartEndIndexPair> startEndIndexPairs = RandomAccessFileUtil.getStartEndIndexPairs(raf, Runtime.getRuntime().availableProcessors());



    }

    public static void writeFile(int count, RandomAccessFile raf) throws UnsupportedEncodingException {
        int nCPU = Runtime.getRuntime().availableProcessors();
        int numberPerCPU = count / nCPU;
        int lastNumberForCPU = count - numberPerCPU * (nCPU - 1);
        long lastIndexOfThread = 0;
        for (int i = 1; i <= nCPU; i++) {
            StringBuilder sb = new StringBuilder();
            if (i < nCPU) {
                for (int j = 0; j < numberPerCPU; j++) {
                    appendContent(sb);
                }
            } else {
                for (int j = 0; j < lastNumberForCPU; j++) {
                    appendContent(sb);
                }
            }
            int length = sb.toString().getBytes(charset).length;
            if (i == 1) {
                lastIndexOfThread = 0;
            } else {
                lastIndexOfThread += length;
            }
            System.out.println(sb.toString());
            new WriterThread("writerThread-" + i, raf, lastIndexOfThread, sb.toString()).start();
        }

    }

    private static void appendContent(StringBuilder sb) {
        sb.append(RandomUtil.getRandomString(5))
                .append(",").append(RandomUtil.getRandomInt(5, 1000000))
                .append(",").append(RandomUtil.getRandomInt(0, 100000)).append("\n");
    }

    static class WriterThread extends Thread {
        private final RandomAccessFile raf;
        private final long startIndex;
        private final String content;

        public WriterThread(String name, RandomAccessFile raf, long startIndex, String content) {
            super(name);
            this.raf = raf;
            this.startIndex = startIndex;
            this.content = content;
        }

        @Override
        public void run() {
            try {
                raf.seek(startIndex);
                byte[] bytes = content.trim().getBytes(charset);
                raf.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
