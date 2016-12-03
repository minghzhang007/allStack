package com.lewis.firstPhase.baseDataStructure;

import com.lewis.sort.SwapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhangminghua on 2016/11/20.
 * 编码实现下面的要求
 * 现有对象 MyItem {byte type,byte color,byte price} ，要求将其内容存放在一个扁平的byte[]数组存储数据的ByteStore {byte[] storeByteArry}对象里,
 * 即每个MyItem占用3个字节，第一个MyItem占用storeByteArry[0]-storeByteArry[2] 3个连续字节，以此类推，最多能存放1000个MyItem对象
 * ByteStore提供如下方法
 * putMyItem(int index,MyItem item) 在指定的Index上存放MyItem的属性，这里的Index是0-999，而不是storeByteArry的Index
 * getMyItem(int index),从指定的Index上查找MyItem的属性，并返回对应的MyItem对象。
 * <p>
 * 要求放入3个MyItem对象（index为0-2）并比较getMyItem方法返回的这些对象是否与之前放入的对象equal。
 * 加分功能如下：
 * 放入1000个MyItem对象到ByteStore中，采用某种算法对storeByteArry做排序，排序以price为基准，排序完成后，输出前100个结果
 */
public class FifthTopic {
    public static void main(String[] args) {
        //basicFunction();
        advanceFunction();
    }

    public static void advanceFunction(){
        Random r = new Random();
        int size = 1000;
        List<MyItem> itemList = new ArrayList<>(size);
        ByteStore byteStore = new ByteStore(new byte[size*3]);
        for (int i = 0; i < size; i++) {
            itemList.add(new MyItem((byte) r.nextInt(128),(byte)r.nextInt(128),(byte)r.nextInt(128)));
        }
        for (int i = 0; i < size; i++) {
            byteStore.putMyItem(i,itemList.get(i));
        }

        byte[] byteArray = byteStore.getStoreByteArray();
        //使用冒泡算法排序
        for (int i = 2; i < byteArray.length; i=i+3) {
            for (int j = i+3; j < byteArray.length; j=j+3) {
                if (byteArray[j] > byteArray[i]) {
                    SwapUtil.xorsSwap(byteArray,j,i);
                    SwapUtil.xorsSwap(byteArray,j-1,i-1);
                    SwapUtil.xorsSwap(byteArray,j-2,i-2);
                }
            }
        }

        List<MyItem> sortedList = new ArrayList<>();
        for (int i = 0; i < byteStore.getSize(); i++) {
            sortedList.add(byteStore.getMyItem(i));
        }
        System.out.println();
        System.out.println("after sort:");
        sortedList.stream().limit(100).forEach(System.out::println);
    }


    public static void basicFunction(){
        MyItem item1= new MyItem((byte)1,(byte)52,(byte)100);
        MyItem item2= new MyItem((byte)2,(byte)56,(byte)110);
        MyItem item3= new MyItem((byte)3,(byte)96,(byte)29);
        ByteStore byteStore = new ByteStore(new byte[3000]);
        byteStore.putMyItem(0,item1);
        byteStore.putMyItem(1,item2);
        byteStore.putMyItem(2,item3);
        MyItem item11 = byteStore.getMyItem(0);
        MyItem item21 = byteStore.getMyItem(1);
        MyItem item31 = byteStore.getMyItem(2);
        System.out.println(item11);
        System.out.println(item1);
        System.out.println();
        System.out.println(item21);
        System.out.println(item2);
        System.out.println();
        System.out.println(item31);
        System.out.println(item3);
        System.out.println("item1.equals(item11) "+item1.equals(item11));
        System.out.println("item2.equals(item21) "+item2.equals(item21));
        System.out.println("item3.equals(item31) "+item3.equals(item31));
    }
}

class ByteStore {
    private byte[] storeByteArray = null;
    private int indexBounds;
    private int size;

    public ByteStore(byte[] storeByteArray) {
        this.storeByteArray = storeByteArray;
        indexBounds = storeByteArray.length / 3 - 1;
    }

    public boolean putMyItem(int index, MyItem item) {
        if (checkIndex(index) && item != null) {
            int byteIndex=index * 3;
            storeByteArray[byteIndex] = item.getType();
            storeByteArray[byteIndex + 1] = item.getColor();
            storeByteArray[byteIndex + 2] = item.getPrice();
            size++;
            return true;
        }
        return false;
    }


    public MyItem getMyItem(int index) {
        if (checkIndex(index)) {
            int byteIndex=index * 3;
            return new MyItem(storeByteArray[byteIndex],storeByteArray[byteIndex+1],storeByteArray[byteIndex+2]);
        }
        return null;
    }

    private boolean checkIndex(int index) {
        return index <= indexBounds;
    }

    public byte[] getStoreByteArray() {
        return storeByteArray;
    }

    public int getSize() {
        return size;
    }
}

class MyItem {
    private byte type;
    private byte color;
    private byte price;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getColor() {
        return color;
    }

    public void setColor(byte color) {
        this.color = color;
    }

    public byte getPrice() {
        return price;
    }

    public void setPrice(byte price) {
        this.price = price;
    }

    public MyItem() {
    }

    public MyItem(byte type, byte color, byte price) {
        this.type = type;
        this.color = color;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyItem)) return false;

        MyItem myItem = (MyItem) o;

        if (type != myItem.type) return false;
        if (color != myItem.color) return false;
        return price == myItem.price;

    }

    @Override
    public int hashCode() {
        int result = (int) type;
        result = 31 * result + (int) color;
        result = 31 * result + (int) price;
        return result;
    }

    @Override
    public String toString() {
        return "MyItem{" +
                "type=" + type +
                ", color=" + color +
                ", price=" + price +
                '}';
    }
}
