package com.lewis.firstPhase.collectionframe;

import java.util.*;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * Created by zhangminghua on 2016/12/19.
 */
public class CollectionDemo {

    public static void main(String[] args) {
       /* testSet();
        testSortedSet();*/
        //testNavigableSet();
        testTransferQueue();
    }

    public static void testSet(){
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            set.add("name_"+i);
        }
        System.out.println(set.toString());
    }

    public static void testSortedSet(){
        SortedSet<String> set = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            set.add("name_"+i);
        }
        set.add("name_0");
        System.out.println(set.toString());
        Comparator<? super String> comparator = set.comparator();
        String first = set.first();
        String last = set.last();
        System.out.println("first:"+first);
        System.out.println("last:"+last);
        SortedSet<String> strings = set.subSet("name_0", "name_4");
        System.out.println(strings);
        SortedSet<String> name_5 = set.tailSet("name_5");
        System.out.println(name_5);
        SortedSet<String> name_51 = set.headSet("name_5");
        System.out.println(name_51);
    }

    public static void testNavigableSet(){
        NavigableSet<Integer> set = new TreeSet<>();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            set.add(r.nextInt(200));
        }
        System.out.println("set:"+set.toString());
        NavigableSet<Integer> descendingSet = set.descendingSet();
        System.out.println("descendingSet:"+descendingSet);
        Iterator<Integer> integerIterator = set.descendingIterator();
        while (integerIterator.hasNext()) {
            Integer next = integerIterator.next();
            System.out.print(next+" ");
        }
        System.out.println();
    }

    public static void testTransferQueue() {
        TransferQueue<String> queue = new LinkedTransferQueue<>();
        /*for (int i = 0; i < 10; i++) {
            queue.add("name_"+i);
        }*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        queue.take();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ).start();
        try {
            System.out.println("wating to tranfer element");
            queue.transfer("name_5");
            System.out.println("transfered element");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
