package com.lewis.firstPhase.multiThreadUp;

import com.lewis.firstPhase.RandomUtil;
import com.lewis.firstPhase.Salary;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by zhangminghua on 2017/1/18.
 * 固定长度
 */
public class AdvancedTopic1 {
    public static final String charset = "utf-8";
    public static final String lineSeparator = System.getProperty("line.separator");
    public static final String comma = ",";
    public static int nCpu = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService exector = Executors.newFixedThreadPool(nCpu);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        long begin = System.currentTimeMillis();
        String path = "D:\\allStack\\3.txt";
        int totatCount =10000_000;
        //writeFile(path,totatCount);
        System.out.println("write costTime:"+(System.currentTimeMillis()-begin));
        begin = System.currentTimeMillis();
        readFile(path, totatCount);
        System.out.println("reader costTime:"+(System.currentTimeMillis()-begin));
    }

    public static void writeFile(String path, int count) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(path, "rw");
        int nameLength = RandomUtil.getRandomString(5).getBytes(charset).length;
        int separatorLength = lineSeparator.getBytes(charset).length;
        int commaLength = ",".getBytes(charset).length;
        int lengthOfBaseSalary = 7;
        int lengthOfBonus = 6;
        int lengthOfOneRecord = nameLength + lengthOfBaseSalary + lengthOfBonus + commaLength * 2 + separatorLength;
        long totalLength = count * lengthOfOneRecord;
        raf.setLength(totalLength);
        raf.seek(0);
        StringBuilder sb = new StringBuilder();
        StringBuilder salaryBuilder = new StringBuilder();

        for (int i = 0; i < count; i++) {
            int baseSalary = RandomUtil.getRandomInt(5, 1000000);
            int bonus = RandomUtil.getRandomInt(0, 100000);
            StringBuilder baseSalaryBuilder = getStringBuilder(salaryBuilder, lengthOfBaseSalary, baseSalary);
            sb.append(RandomUtil.getRandomString(5)).append(comma).append(baseSalaryBuilder.toString()).append(comma);
            baseSalaryBuilder.delete(0, baseSalaryBuilder.length());
            baseSalaryBuilder = getStringBuilder(salaryBuilder, lengthOfBonus, bonus);
            sb.append(baseSalaryBuilder.toString()).append(lineSeparator);
            baseSalaryBuilder.delete(0, baseSalaryBuilder.length());
            byte[] bytes = sb.toString().getBytes(charset);
            raf.write(bytes);
            sb.delete(0, sb.toString().length());
        }


    }

    public static void readFile(String path, int count) throws IOException, ExecutionException, InterruptedException {
        List<Salary> list = new LinkedList<>();
        RandomAccessFile raf = new RandomAccessFile(path, "rw");
        nCpu = nCpu << 2;
        int countPerThread = count / nCpu;
        long lastIndexOfThread = 0;
        long totalLength = 0;
        System.out.println("realLength:"+raf.length());
        List<Future<List<Salary>>> futureList = new ArrayList<>(nCpu);
        for (int i = 1; i <= nCpu; i++) {
            if (i == 1) {
                lastIndexOfThread = 0;
            } else {
                lastIndexOfThread = totalLength;
            }
            totalLength += countPerThread * 22;
            Future<List<Salary>> future = exector.submit(new ReaderTask(path, countPerThread, lastIndexOfThread));
            futureList.add(future);
        }
        for (Future<List<Salary>> future : futureList) {
            list.addAll(future.get());
        }
        System.out.println(list.size());
    }

    static class ReaderTask implements Callable<List<Salary>> {
        private final String path;
        private final int count;
        private final long startIndex;

        public ReaderTask(String path, int count, long startIndex) {
            this.path = path;
            this.count = count;
            this.startIndex = startIndex;
        }

        @Override
        public List<Salary> call() throws Exception {
            RandomAccessFile raf = new RandomAccessFile(path,"rw");
            List<Salary> list = new ArrayList<>(count);
            System.out.println(Thread.currentThread().getName()+"start : "+startIndex+"-"+(startIndex+count*22));
            int index = 0;
            byte[] namePreBytes = new byte[2];
            byte[] baseSalaryBytes = new byte[7];
            byte[] bonusBytes = new byte[6];
            while (index < count) {
                raf.seek(index * 22 + startIndex);
                raf.read(namePreBytes);
                raf.seek(startIndex + index * 22+6);
                raf.read(baseSalaryBytes);
                raf.seek(startIndex + index * 22+14);
                raf.read(bonusBytes);
                index++;
                String namePre = new String(namePreBytes, charset);
                String baseSalary = new String(baseSalaryBytes, charset);
                String bonus = new String(bonusBytes, charset);
                list.add(new Salary(namePre,
                        Integer.parseInt(baseSalary.trim()), Integer.parseInt(bonus.trim())));
            }
            System.out.println(Thread.currentThread().getName()+"end : "+startIndex+"-"+(startIndex+count*22));
            Map<String,Long> name2TotalSalary = new HashMap<>();
            Map<String,Integer> name2CountMap = new HashMap<>();
            for (Salary salary : list) {
                Long baseSalary = name2TotalSalary.get(salary.getName());
                if (baseSalary == null) {
                    name2TotalSalary.put(salary.getName(),Long.valueOf(getTotalSalary(salary)));
                }else{
                    name2TotalSalary.put(salary.getName(),baseSalary+getTotalSalary(salary));
                }
                Integer count = name2CountMap.get(salary.getName());
                if (count == null) {
                    name2CountMap.put(salary.getName(),1);
                }else{
                    name2CountMap.put(salary.getName(),count+1);
                }

            }
            return list;
        }

        private Integer getTotalSalary(Salary salary) {
            return salary.getBaseSalary()*13+salary.getBonus();
        }
    }

    private static StringBuilder getStringBuilder(StringBuilder baseSalaryBuilder, int lengthOfBaseSalary, int baseSalary) {
        String baseSalaryStr = String.valueOf(baseSalary);
        baseSalaryBuilder.append(baseSalaryStr);
        int realLengthOfBaseSalary = baseSalaryStr.length();
        if (realLengthOfBaseSalary < lengthOfBaseSalary) {
            int needAppendNumber = lengthOfBaseSalary - realLengthOfBaseSalary;
            for (int j = needAppendNumber; j > 0; j--) {
                baseSalaryBuilder.append(" ");
            }
        }
        return baseSalaryBuilder;
    }
}
