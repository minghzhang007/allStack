package com.lewis.firstPhase.baseDataStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhangminghua on 2016/12/3.
 * 第五题中的 storeByteArry改为int[]数组，
 * 采用Java位操作方式来实现1个Int 拆解为4个Byte，存放MyItem对象的属性。
 */
public class AdvancedFifthTopic {
    public static void main(String[] args) {
        AdvancedFifthTopic advancedFifthTopic = new AdvancedFifthTopic();
        advancedFifthTopic.doWork();
    }

    public void doWork(){
        Random r = new Random();
        int size = 10;
        List<MyItem> itemList = new ArrayList<>(size);
        ByteStore byteStore = new ByteStore(new int[size]);
        for (int i = 0; i < size; i++) {
            itemList.add(new MyItem((byte) r.nextInt(128),(byte)r.nextInt(128),(byte)r.nextInt(128)));
        }

        for (int i = 0; i < size; i++) {

        }

    }


    private class ByteStore{
        private int[] storeByteArray;

        public ByteStore(int[] storeByteArray) {
            this.storeByteArray = storeByteArray;
        }

        public boolean putMyItem(int index, MyItem item) {
            if (true) {
                byte type = item.getType();
                byte color = item.getColor();
                byte price = item.getPrice();
                return true;
            }
            return false;
        }

        public int[] getStoreByteArray() {
            return storeByteArray;
        }

        public void setStoreByteArray(int[] storeByteArray) {
            this.storeByteArray = storeByteArray;
        }
    }
}



