package com.lewis.firstPhase.multiThreadUp;

import com.lewis.firstPhase.RandomUtil;
import com.lewis.firstPhase.Salary;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

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
        int totatCount = 10000_000;
        //writeFile(path,totatCount);
        System.out.println("write costTime:" + (System.currentTimeMillis() - begin));
        begin = System.currentTimeMillis();
        readFile(path, totatCount);
        System.out.println("reader costTime:" + (System.currentTimeMillis() - begin));
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
        List<Counter> list = new LinkedList<>();
        RandomAccessFile raf = new RandomAccessFile(path, "rw");
        //nCpu = nCpu << 2;
        int countPerThread = count / nCpu;
        long lastIndexOfThread = 0;
        long totalLength = 0;
        System.out.println("realLength:" + raf.length());
        List<Future<Counter>> futureList = new ArrayList<>(nCpu);
        for (int i = 1; i <= nCpu; i++) {
            if (i == 1) {
                lastIndexOfThread = 0;
            } else {
                lastIndexOfThread = totalLength;
            }
            totalLength += countPerThread * 22;
            Future<Counter> future = exector.submit(new ReaderTask(path, countPerThread, lastIndexOfThread));
            futureList.add(future);
        }
        for (Future<Counter> future : futureList) {
            list.add(future.get());
        }
        Map<String, Long> totalName2TotalSalary = new HashMap<>();
        Map<String, Integer> totalName2CountMap = new HashMap<>();
        int index = 0;
        for (Counter counter : list) {
            Map<String, Long> name2TotalSalary = counter.getName2TotalSalary();
            Map<String, Integer> name2CountMap = counter.getName2CountMap();
            if (index == 0) {
                totalName2TotalSalary.putAll(name2TotalSalary);
                totalName2CountMap.putAll(name2CountMap);
            } else {
                megerLongMap(totalName2TotalSalary, name2TotalSalary);
                megerIntegerMap(totalName2CountMap,name2CountMap);
            }
            index++;
        }
        AtomicLong counter = new AtomicLong();
        Iterator<Map.Entry<String, Integer>> iterator = totalName2CountMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            counter.addAndGet(entry.getValue());
        }
        System.out.println(counter.get());
    }

    private static void megerIntegerMap(Map<String, Integer> totalName2CountMap, Map<String, Integer> name2CountMap) {
        if (name2CountMap != null && name2CountMap.size() > 0) {
            Iterator<Map.Entry<String, Integer>> iterator = name2CountMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> entry = iterator.next();
                Integer value = totalName2CountMap.get(entry.getKey());
                if (value == null) {
                    totalName2CountMap.put(entry.getKey(),entry.getValue());
                }else{
                    totalName2CountMap.put(entry.getKey(),entry.getValue()+value);
                }
            }
        }
    }

    private static void megerLongMap(Map<String, Long> totalName2TotalSalary, Map<String, Long> name2TotalSalary) {
        if (name2TotalSalary != null && name2TotalSalary.size() > 0) {
            Iterator<Map.Entry<String, Long>> iterator = name2TotalSalary.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Long> entry = iterator.next();
                Long value = totalName2TotalSalary.get(entry.getKey());
                if (value == null) {
                    totalName2TotalSalary.put(entry.getKey(),entry.getValue());
                }else{
                    totalName2TotalSalary.put(entry.getKey(),entry.getValue()+value);
                }
            }
        }
    }

    static class ReaderTask implements Callable<Counter> {
        private final String path;
        private final int count;
        private final long startIndex;

        public ReaderTask(String path, int count, long startIndex) {
            this.path = path;
            this.count = count;
            this.startIndex = startIndex;
        }

        @Override
        public Counter call() throws Exception {
            Map<String, Long> name2TotalSalary = new HashMap<>();
            Map<String, Integer> name2CountMap = new HashMap<>();
            RandomAccessFile raf = new RandomAccessFile(path, "rw");
            System.out.println(Thread.currentThread().getName() + "start : " + startIndex + "-" + (startIndex + count * 22));
            int index = 0;
            byte[] namePreBytes = new byte[2];
            byte[] baseSalaryBytes = new byte[7];
            byte[] bonusBytes = new byte[6];
            while (index < count) {
                raf.seek(index * 22 + startIndex);
                raf.read(namePreBytes);
                raf.seek(startIndex + index * 22 + 6);
                raf.read(baseSalaryBytes);
                raf.seek(startIndex + index * 22 + 14);
                raf.read(bonusBytes);
                index++;
                String namePre = new String(namePreBytes, charset);
                int baseSalary = Integer.parseInt(new String(baseSalaryBytes, charset).trim());
                int bonus = Integer.parseInt((new String(bonusBytes, charset)).trim());
                int totalSalay = baseSalary * 13 + bonus;
                Long totalBaseSalary = name2TotalSalary.get(namePre);
                if (totalBaseSalary == null) {
                    name2TotalSalary.put(namePre, Long.valueOf(totalSalay));
                } else {
                    name2TotalSalary.put(namePre, totalSalay + totalBaseSalary);
                }
                Integer count = name2CountMap.get(namePre);
                if (count == null) {
                    name2CountMap.put(namePre, 1);
                } else {
                    name2CountMap.put(namePre, count + 1);
                }
            }
            System.out.println(Thread.currentThread().getName() + "end : " + startIndex + "-" + (startIndex + count * 22));
            Counter counter = new Counter(name2TotalSalary, name2CountMap);
            return counter;
        }

        private Integer getTotalSalary(Salary salary) {
            return salary.getBaseSalary() * 13 + salary.getBonus();
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

    static class Counter {
        final Map<String, Long> name2TotalSalary;
        final Map<String, Integer> name2CountMap;

        public Counter(Map<String, Long> name2TotalSalary, Map<String, Integer> name2CountMap) {
            this.name2TotalSalary = name2TotalSalary;
            this.name2CountMap = name2CountMap;
        }

        public Map<String, Long> getName2TotalSalary() {
            return name2TotalSalary;
        }

        public Map<String, Integer> getName2CountMap() {
            return name2CountMap;
        }
    }
}
