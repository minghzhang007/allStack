package com.lewis.firstPhase.io.advanceFirstTopic;

/**
 * Created by zhangminghua on 2016/12/7.
 * 加密工具类：基于异或运算的简单加密、解密
 */
public class EnDecodeUtil {

    private static final char password='a';

    public static String encodeDecode(String str){
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] ^ password);
        }
        return new String(chars);
    }
}
