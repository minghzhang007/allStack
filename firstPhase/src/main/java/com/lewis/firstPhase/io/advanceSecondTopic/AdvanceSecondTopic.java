package com.lewis.firstPhase.io.advanceSecondTopic;

import com.lewis.firstPhase.RandomUtil;
import com.lewis.firstPhase.Salary;
import com.lewis.firstPhase.multiThreadUp.RandomAccessFileUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by zhangminghua on 2016/12/7.
 */
public class AdvanceSecondTopic {

    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        advanceFunction();
        System.out.println("CostTime is#" + (System.currentTimeMillis() - beginTime));
    }

    public static void advanceFunction() {
        File file = new File("D:\\allStack\\advanceSalarys.txt");
        //generateSalaryFile(file, 10000000);
        try {
            readSalaryFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void readSalaryFile(File file) throws FileNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        List<StartEndIndexPair> startEndIndexPairs = RandomAccessFileUtil.getStartEndIndexPairs(raf,Runtime.getRuntime().availableProcessors());
        //List<Salary> salaryList = getSalariesConcurrent(file, startEndIndexPairs);
        List<Salary> salaryList = getSalariesSerial(raf, startEndIndexPairs);
        System.out.println("size:" + salaryList.size());
    }

    private static List<Salary> getSalariesSerial(RandomAccessFile raf, List<StartEndIndexPair> startEndIndexPairs) {
        List<Salary> salaryList = new LinkedList<>();
        for (StartEndIndexPair startEndIndexPair : startEndIndexPairs) {
            salaryList.addAll(getSalary(raf,startEndIndexPair));
        }
        return salaryList;
    }

    private static List<Salary> getSalariesConcurrent(final File file, List<StartEndIndexPair> startEndIndexPairs) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CountDownLatch latch = new CountDownLatch(startEndIndexPairs.size());
        List<Future<List<Salary>>> futureList = new ArrayList<>(startEndIndexPairs.size());
        for (StartEndIndexPair startEndIndexPair : startEndIndexPairs) {
            futureList.add(executorService.submit(new Callable<List<Salary>>() {
                @Override
                public List<Salary> call() throws Exception {
                    try {
                        RandomAccessFile raf = new RandomAccessFile(file, "rw");
                        return getSalary(raf, startEndIndexPair);
                    } finally {
                        latch.countDown();
                    }
                }
            }));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Salary> salaryList = new LinkedList<>();
        for (Future<List<Salary>> future : futureList) {
            try {
                List<Salary> result = future.get();
                if (result != null) {
                    salaryList.addAll(result);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(5,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return salaryList;
    }



    public static List<Salary> getSalary(RandomAccessFile raf, StartEndIndexPair startEndIndexPair) {
        long startIndex = startEndIndexPair.startIndex;
        long endIndex = startEndIndexPair.getEndIndex();
        List<Salary> salaryList = new LinkedList<>();
        try {
            raf.seek(startIndex);
            int size = (int) (endIndex - startIndex + 1);
            byte[] bytes = new byte[size];
            raf.read(bytes);
            String str = new String(bytes);
            String[] array = str.split("\n");
            if (array != null && array.length > 0) {
                for (String line : array) {
                    String[] ss = line.trim().split(",");
                    if (ss != null && ss.length ==3) {
                        Salary salary = new Salary();
                        salary.setName(ss[0]);
                        salary.setBaseSalary(Integer.parseInt(ss[1]));
                        salary.setBonus(Integer.parseInt(ss[2]));
                        salaryList.add(salary);
                    }else if (ss.length < 3) {
                        writeErrorString(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return salaryList;
    }

    private static void writeErrorString(String line) throws IOException {
        File errorFile = new File("D:\\allStack\\error-"+Thread.currentThread().getId()+".txt");
        FileWriter fileWriter = new FileWriter(errorFile,true);
        fileWriter.write(line+"\n");
        System.out.println(line);
        fileWriter.flush();
        fileWriter.close();
    }


    private static void generateSalaryFile(File file, int count) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileChannel channel = raf.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1 * 1024 * 1024);
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < count; i++) {
                sb.append(RandomUtil.getRandomString(5))
                        .append(",").append(RandomUtil.getRandomInt(5, 1000000))
                        .append(",").append(RandomUtil.getRandomInt(0, 100000)).append("\n");
                byte[] bytes = sb.toString().getBytes();
                // System.out.println("count:"+(i+1)+"  "+sb.toString());

                sb.delete(0, sb.toString().length());
                if (buffer.remaining() > bytes.length) {
                    buffer.put(bytes);
                } else {
                    buffer.flip();
                    channel.write(buffer);
                    buffer.clear();
                }
            }
            buffer.flip();
            channel.write(buffer);
            //channel.force(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
