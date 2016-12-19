package com.lewis.firstPhase.io;

import com.lewis.firstPhase.RandomUtil;
import com.lewis.firstPhase.Salary;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhangminghua on 2016/11/20.
 * 4  随机生成 Salary {name, baseSalary, bonus  }的记录，如“wxxx,10,1”，每行一条记录，总共1000万记录，写入文本文件（UFT-8编码），
 * 然后读取文件，name的前两个字符相同的，其年薪累加，比如wx，100万，3个人，最后做排序和分组，输出年薪总额最高的10组：
 * wx, 200万，10人
 * lt, 180万，8人
 * ....
 */
public class FourthTopic {
    public static void main(String[] args) {
        File file = new File("D:\\allStack\\salarys.txt");
        long beginTime = System.currentTimeMillis();
        List<Salary> salaryList = readSalariesFromFile(file);
        basicFunction(salaryList);
        System.out.println("costTime is #"+(System.currentTimeMillis()-beginTime));
    }

    public static void basicFunction(List<Salary> salaryList){
        System.out.println(salaryList.size());
        Map<String, List<Salary>> map = salaryList.stream().collect(Collectors.groupingBy(salary -> salary.getName().substring(0, 2)));
        Map<String,Long> name2TotalMoneyMap = new HashMap<>();
        Map<String,Integer> name2CountMap = new HashMap<>();
        if (map != null && map.size() > 0) {
            Iterator<Map.Entry<String, List<Salary>>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Long totalMoney = 0L;
                Integer count = 0;
                Map.Entry<String, List<Salary>> entry = it.next();
                if (!entry.getValue().isEmpty()) {
                    for (Salary salary : entry.getValue()) {
                        totalMoney +=getSalary(salary);
                        count ++;
                    }
                }
                name2CountMap.put(entry.getKey(),count);
                name2TotalMoneyMap.put(entry.getKey(),totalMoney);
            }
        }
        List<Map.Entry<String,Long>> list = new LinkedList<>();
        list.addAll(name2TotalMoneyMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        List<Map.Entry<String, Long>> topTenList = list.stream().limit(100).collect(Collectors.toList());
        topTenList.stream().forEach( e -> {
            String key = e.getKey();
            Long value = e.getValue();
            Integer count = name2CountMap.get(key);
            System.out.println(key+","+value/10000+"万"+","+count+"个人");
        });
    }

    public static int getSalary(Salary salary){
        return salary.getBaseSalary() * 13 + salary.getBonus();
    }

    public static List<Salary> readSalariesFromFile(File file) {
        List<Salary> salaryList = new LinkedList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (true) {
                String readLine = br.readLine();
                if (readLine == null || readLine.equals("")) {
                    break;
                }
                String[] ss = readLine.split(",");
                if (ss != null && ss.length == 3) {
                    Salary salary = createSalary(ss);
                    salaryList.add(salary);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return salaryList;
    }
    private static Salary createSalary(String[] ss) {
        Salary salary = new Salary();
        salary.setName(ss[0]);
        salary.setBaseSalary(Integer.parseInt(ss[1]));
        salary.setBonus(Integer.parseInt(ss[2]));
        return salary;
    }

    private static void generateSalaryFile(File file,int count) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("utf-8")));
            StringBuilder sb = new StringBuilder();
            int batchSize =10000;
            int batchIndex = 0;
            for (int i = 0; i < count; i++) {
                sb.append(RandomUtil.getRandomString(5))
                        .append(",").append(RandomUtil.getRandomInt(5,1000000))
                        .append(",").append(RandomUtil.getRandomInt (0,100000)).append("\n");
                bw.write(sb.toString());
                sb.delete(0,sb.toString().length());
                batchIndex ++;
                if (batchIndex == batchSize) {
                    bw.flush();
                    batchIndex=0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


