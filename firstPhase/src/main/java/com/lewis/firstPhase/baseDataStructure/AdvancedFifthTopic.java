package com.lewis.firstPhase.baseDataStructure;

import com.lewis.sort.BinaryUtil;

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

    public void doWork() {
        Random r = new Random();
        int size = 10;
        List<MyItem> itemList = new ArrayList<>(size);
        ByteStore byteStore = new ByteStore(new int[size]);
        for (int i = 0; i < size; i++) {
            itemList.add(new MyItem((byte) r.nextInt(128), (byte) r.nextInt(128), (byte) r.nextInt(128)));
        }
        itemList.stream().forEach(System.out::println);
        for (int i = 0; i < size; i++) {
            byteStore.putMyItem(i,itemList.get(i));
        }

        System.out.println();
        System.out.println();
        List<MyItem> getList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            getList.add(byteStore.getMyItem(i));
        }
        getList.stream().forEach(System.out::println);
        int[] storeByteArray = byteStore.getStoreByteArray();


    }


    private class ByteStore {
        private int[] storeByteArray;

        public ByteStore(int[] storeByteArray) {
            this.storeByteArray = storeByteArray;
        }

        public boolean putMyItem(int index, MyItem item) {
            byte[] bytes = new byte[4];
            bytes[0] = item.getType();
            bytes[1] = item.getColor();
            bytes[2] = item.getPrice();
            bytes[3] = 0;
            int intValue = BinaryUtil.byteArray2Int(bytes);
            setValue(index,intValue);
            return true;
        }

        public MyItem getMyItem(int index) {
            if (checkIndexForPass(index)) {
                int intValue = storeByteArray[index];
                byte[] bytes = BinaryUtil.int2ByteArray(intValue);
                MyItem item = new MyItem();
                item.setType(bytes[0]);
                item.setColor(bytes[1]);
                item.setPrice(bytes[2]);
                return item;
            }
            return null;
        }

        public boolean setValue(int index, int value) {
            if (checkIndexForPass(index)) {
                storeByteArray[index] = value;
                return true;
            }
            return false;
        }



        private boolean checkIndexForPass(int index) {
            return index >= 0 && index <= storeByteArray.length - 1;
        }

        public int[] getStoreByteArray() {
            return storeByteArray;
        }

        public void setStoreByteArray(int[] storeByteArray) {
            this.storeByteArray = storeByteArray;
        }
    }
}



