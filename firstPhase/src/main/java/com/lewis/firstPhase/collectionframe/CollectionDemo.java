package com.lewis.firstPhase.collectionframe;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
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
        //testTransferQueue();
        //testQueue();
       // testLinkedList();
        //testArray2Collection();
        testHashMap();
    }

    public static void testHashMap(){
        Person person1 = new Person(1,"name1","singsing");
        Person person2 = new Person(1,"name1","singsing");
        Person person3 = new Person(1,"name1","singsing");
        Person person4 = new Person(1,"name1","singsing");
        HashMap<Person,Integer> map = new HashMap<>();
        map.put(person1,1);
        map.put(person2,2);
        map.put(person3,3);
        map.put(person4,4);
        System.out.println(map.toString());
        HashSet<Person> set = new HashSet<>();
        set.add(person1);
        set.add(person2);
        set.add(person3);
        set.add(person4);
        System.out.println(set.toString());
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());
        System.out.println(person3.hashCode());
        System.out.println(person4.hashCode());
    }

    public static void testArray2Collection(){
        String[] array = new String[10];
        for (int i = 0; i < 10; i++) {
            array[i]="name_"+i;
        }
        List<String> strings = Arrays.asList(array);
        System.out.println(strings);
        strings.remove(0);
        //strings.add("name_11");
        System.out.println(strings);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println(list);
        list.toArray();
    }

    private static void testQueue() {
        Queue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
        for (int i = 0; i < 20; i++) {
            //queue.add(i);
            boolean offer = queue.offer(i);
            System.out.println(offer);
        }
        System.out.println();
        System.out.println();
        for (int i = 0; i < 20; i++) {
            //Integer remove = queue.remove();
            Integer poll = queue.poll();
            System.out.println(poll);
        }
        queue.add(1);
        Integer element = queue.element();
        System.out.println(element);
        System.out.println("size:"+queue.size());
    }

    public static void testLinkedList(){
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add("name_"+i);
        }
        System.out.println(list.toString());
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
