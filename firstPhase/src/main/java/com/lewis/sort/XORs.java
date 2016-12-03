package com.lewis.sort;

/**
 * Created by zhangminghua on 2016/12/3.
 *
 * 异或运算^ 满足：
 * 1.交换律 a ^ b = b ^ a
 * 2.结合律 (a ^ b)^c= a ^ (b ^ c)
 * 3.对于任何数都有 a ^ a = 0;a ^ 0 = a;
 * 4.自反性 a ^ b ^ b = a ^ 0 = a
 */
public class XORs {

    public static void main(String[] args) {
        String name ="顶级机密：1941年12月 日本偷袭珍珠港！ 银行密码是：12345678990";
        String encodeName = encode(name);
        System.out.println("加密："+encodeName);
        String decode = decode(encodeName);
        System.out.println("解密："+decode);
    }

    private static final char p = 'a';

    public static String encode(String str){
        if (str != null && str.length() > 0) {
            return encodeDecode(str);
        }
        return str;
    }

    private static String encodeDecode(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] ^ p);
        }
        return new String(chars);
    }

    public static String decode(String str){
        if (str != null && str.length() > 0) {
           return encodeDecode(str);
        }
        return str;
    }
}
