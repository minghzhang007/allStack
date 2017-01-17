package com.lewis.firstPhase.multiThreadUp;

import com.lewis.firstPhase.RandomUtil;
import com.lewis.firstPhase.Salary;
import com.lewis.firstPhase.io.advanceSecondTopic.StartEndIndexPair;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by zhangminghua on 2017/1/17.
 * 多线程版：随机生成 Salary {name, baseSalary, bonus  }的记录，如“wxxx,10,1”，每行一条记录，总共1000万记录，写入文本文件（UFT-8编码），
 * 然后读取文件，name的前两个字符相同的，其年薪累加，比如wx，100万，3个人，最后做排序和分组，输出年薪总额最高的10组：
 * wx, 200万，10人
 * lt, 180万，8人
 */
public class AdvancedTopic {

    private static final String charset = "utf-8";
    private static final  String lineSeparator = System.getProperty("line.separator");
    private static final int coreCPUSize = Runtime.getRuntime().availableProcessors();
    private static ExecutorService executorService = Executors.newFixedThreadPool(coreCPUSize/2);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        String path = "D:\\allStack\\2.txt";
        //writeFile(10000000,path);
        List<Salary> salaries = readFile(path);
        System.out.println("size:"+salaries.size());
        System.out.println("lineSeparator="+lineSeparator);
        System.out.println("costTime:" + (System.currentTimeMillis() - start));
    }

    public static List<Salary> readFile(String path) throws IOException, ExecutionException, InterruptedException {
        RandomAccessFile raf = new RandomAccessFile(path,"rw");
        List<Salary> totalSalaryList = new LinkedList<>();
        List<StartEndIndexPair> startEndIndexPairs = RandomAccessFileUtil.getStartEndIndexPairs(raf,  coreCPUSize *2);
        System.out.println(startEndIndexPairs);
        List<Future<List<Salary>>> futures = new ArrayList<>(startEndIndexPairs.size());
        for (StartEndIndexPair startEndIndexPair : startEndIndexPairs) {
            Future<List<Salary>> future = executorService.submit(new ReaderTask(raf, startEndIndexPair));
            futures.add(future);
        }
        for (Future<List<Salary>> future : futures) {
            List<Salary> salaries = future.get();
            if (salaries != null && salaries.size() > 0) {
                totalSalaryList.addAll(salaries);
            }
        }
        return totalSalaryList;
    }


    public static void writeFile(int count, String path) throws FileNotFoundException, UnsupportedEncodingException {
        int nCPU = coreCPUSize*3;
        int numberPerCPU = count / nCPU;
        int lastNumberForCPU = count - numberPerCPU * (nCPU - 1);
        long lastIndexOfThread = 0;
        long totalLength =0;
        StringBuilder sb = new StringBuilder();
        List<Pair> contents = new ArrayList<>(nCPU);
        for (int i = 1; i <= nCPU; i++) {
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
                lastIndexOfThread = totalLength;
            }
            totalLength += length;
            contents.add(new Pair(lastIndexOfThread,length,sb.toString()));
            sb.delete(0,sb.toString().length()-1);
        }
        final long finalTotalLength = totalLength;
        contents.stream().forEach(x-> {
            executorService.submit(new WriterTask(path,x.lastIndex,x.content,finalTotalLength));
        });

    }

    private static void appendContent(StringBuilder sb) {
        sb.append(RandomUtil.getRandomString(5))
                .append(",").append(RandomUtil.getRandomInt(5, 1000000))
                .append(",").append(RandomUtil.getRandomInt(0, 100000)).append(lineSeparator);
    }

    static class WriterTask implements Runnable {
        private final String path;
        private final long startIndex;
        private final String content;
        private final long totalLength;

        public WriterTask(String path, long startIndex, String content, long totalLength) {
            this.path = path;
            this.startIndex = startIndex;
            this.content = content;
            this.totalLength = totalLength;
        }

        @Override
        public void run() {
            try {
                RandomAccessFile raf = new RandomAccessFile(path,"rw");
                System.out.println("totalLength="+totalLength);
                raf.setLength(totalLength);
                raf.seek(startIndex);
                byte[] bytes = content.getBytes(charset);
                raf.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ReaderTask implements Callable<List<Salary>>{
        private final RandomAccessFile raf;
        private final StartEndIndexPair startEndIndexPair;
        public ReaderTask(RandomAccessFile raf, StartEndIndexPair startEndIndexPair) {
            this.raf = raf;
            this.startEndIndexPair = startEndIndexPair;
        }

        @Override
        public List<Salary> call() throws Exception {
            List<Salary> salaries = new LinkedList<>();
            long startIndex = startEndIndexPair.getStartIndex();
            long endIndex = startEndIndexPair.getEndIndex();
            raf.seek(startIndex);
            int size = (int) (endIndex-startIndex+1);
            byte[] buffer = new byte[size];
            raf.read(buffer);
            String str = new String(buffer,charset);
            String[] lineRecordArray = str.split(lineSeparator);
            if (lineRecordArray != null && lineRecordArray.length > 0) {
                for (String lineRecord : lineRecordArray) {
                    String[] array = lineRecord.split(",");
                    if (array != null && array.length == 3) {
                        salaries.add(new Salary(array[0],Integer.parseInt(array[1]),Integer.parseInt(array[2])));
                    }else{
                        System.out.println("errorLine: "+lineRecord);
                    }
                }
            }
            return salaries;
        }
    }

    static class Pair{
        private long lastIndex;
        private String content;
        private long length;

        public Pair(long lastIndex,long length, String content) {
            this.lastIndex = lastIndex;
            this.length = length;
            this.content = content;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "lastIndex=" + lastIndex +
                    ", length=" + length +
                    '}';
        }
    }
}
