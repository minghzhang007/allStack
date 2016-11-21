package com.lewis.firstPhase.io;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangminghua on 2016/11/20.
 * 1  得到 String s="中国" 这个字符串的utf-8编码，gbk编码，iso-8859-1编码的字符串，看看各自有多少字节，
 * 同时解释为什么以utf-8编码得到的byte[]无法用gbk的方式“还原”为原来的字符串
 * 以某种格式编码的得到的byte[]，必须用相同的格式进行解码才能还原为原来的数据；
 * 所以用utf-8编码得到的byte[]，不能用其他的格式来还原为原来的字符串
 * 汉字：若utf-8编码，一个汉字3个字节
 * 若gbk编码，一个汉字2个字节
 * 若iso-8859-1编码，一个汉字1个字节
 */
public class FirstTopic {
    public static void main(String[] args) {
        String china ="中国的奋斗";
        try {
            byte[] utf8Bytes = china.getBytes("utf-8");
            byte[] gbkBytes = china.getBytes("gbk");
            byte[] iso5559_1Bytes = china.getBytes("iso-8859-1");
            System.out.println("utf8 byte length "+utf8Bytes.length);
            System.out.println("gbk byte length "+gbkBytes.length);
            System.out.println("iso8859_1 byte length "+iso5559_1Bytes.length);
            String gbk = new String(utf8Bytes, "gbk");
            System.out.println(gbk);
            System.out.println(new String(utf8Bytes,"utf-8"));
            System.out.println(new String(utf8Bytes,"iso-8859-1"));
            System.out.println(new String(iso5559_1Bytes,"gbk"));
            System.out.println(new String(gbkBytes,"iso-8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
