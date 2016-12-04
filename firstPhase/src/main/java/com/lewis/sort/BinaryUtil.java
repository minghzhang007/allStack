package com.lewis.sort;

/**
 * Created by Administrator on 2016/12/4.
 */
public class BinaryUtil {

    /**
     * 将一个int拆成4个byte组成的数组 【第一个byte 高8位】  【第二个byte 次高8位】  【第三个byte 次低8位】 【第四个byte 低8位】
     *
     * @param i
     * @return 4个byte组成的数组
     */
    public static byte[] int2ByteArray(int i) {
        byte[] bytes = new byte[4];
        //第一个byte 高8位
        bytes[0] = (byte) ((i >> 24) & 0xff);
        //第二个byte 次高8位
        bytes[1] = (byte) ((i >> 16) & 0xff);
        //第三个byte 次低8位
        bytes[2] = (byte) ((i >> 8) & 0xff);
        //第四个byte 低8位
        bytes[3] = (byte) ((i) & 0xff);
        return bytes;
    }

    /**
     * 将byte数组的byte按照从高位到低位的次序排练，行程一个int
     *
     * @param bytes
     * @return
     */
    public static int byteArray2Int(byte[] bytes) {
        //int i = 0;
        //0011 1011  1001 1010  1100 1010  0000 0000
        int v0 = (bytes[0] << 24) & 0xff000000;
        int v1 = (bytes[1] << 16) & 0x00ff0000;
        int v2 = (bytes[2] << 8) & 0x0000ff00;
        int v3 = bytes[3] & 0x000000ff;
        //i = v0 + v1 + v2 + v3;
        //int i = v0|v1|v2|v3;
        return v0|v1|v2|v3;
    }

    public static String getBinaryString(String str) {
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            char[] chars = str.toCharArray();
            int index = 0;
            if (chars != null && chars.length > 0) {
                for (int i = chars.length - 1; i >= 0; i--) {
                    if (index == 4) {
                        sb.append(" ");
                        index = 0;
                    }
                    sb.append(chars[i]);
                    index++;
                }
            }
            String s = sb.reverse().toString();
            String[] split = s.split(" ");
            if (split != null && split.length > 0) {
                char[] chars1 = split[0].toCharArray();
                int length = chars1.length;
                for (int i = 0; i < 4 - length; i++) {
                    sb.insert(0, '0');
                }
            }
            return sb.toString();
        }
        return "";
    }
}
